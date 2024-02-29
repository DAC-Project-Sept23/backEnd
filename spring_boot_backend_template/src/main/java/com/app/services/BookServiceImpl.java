package com.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.EbookDto;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.entities.Ebook;
import com.app.entities.Genre;
import com.app.entities.Rating;
import com.app.entities.Rejected;
import com.app.entities.Status;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.RatingRepository;
import com.app.repositories.TransactionRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class BookServiceImpl implements BookService {

	private final String uploadDir = "";
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private TransactionRepository transRepo;
	@Autowired
	private RatingRepository ratingRepo;
	@Override
	public ResponseEntity<String> uploadBook(EbookDto ebook) throws IOException {
		if (!ebook.getEpubFile().getOriginalFilename().endsWith(".epub")) {
			return ResponseEntity.badRequest().body("Ebook to upload must be a .epub file.");
		}
		String currentWorkingDir = System.getProperty("user.dir");
		String uploadDir = "src/main/resources/static";
		File bookDir = new File(currentWorkingDir, uploadDir + File.separator + "books");
		File imageDir = new File(currentWorkingDir, uploadDir + File.separator + "cover_images");
		if (!bookDir.exists() && !bookDir.mkdirs())// bookDir.exists()-->check bookDir present or not if not then
													// -->bookDir.mkdirs() create dir
		{
			return ResponseEntity.status(500).body("Failed to create book directory.");
		}
		if (!imageDir.exists() && !imageDir.mkdirs())// imageDir.exists()-->check imageDir present or not if not then
														// -->imageDir.mkdirs() create dir
		{
			return ResponseEntity.status(500).body("Failed to create image directory.");
		}

		String epubFileName = "epub_" + System.currentTimeMillis() + ".epub";
		String epubFilePath = new File(bookDir, epubFileName).getAbsolutePath();
		System.out.println("Epub file path : " + epubFilePath.toString());
		ebook.getEpubFile().transferTo(new File(epubFilePath));// E:\CDAC\Project\spring_boot_backend_template\
																// uploaded_files\books\epub_1707245561300.epub

		String coverImageName = "cover_" + System.currentTimeMillis() + ".jpg";
		// Constructing a coverImageName based on current time in millisec
		String coverImagePath = new File(imageDir, coverImageName).getAbsolutePath();
		ebook.getCoverImage().transferTo(new File(coverImagePath));
		// Transfer the received file to the given destination file. //
		// ebook.getCoverImage()-->new File(coverImagePath)
		Path baseDirPath = Paths.get(currentWorkingDir);
		System.out.println("BaseDir Path : " + baseDirPath.toString()); // Base dir abs path -->
																		// E:\CDAC\Project\spring_boot_backend_template
		Path relativeEpubPath = baseDirPath.relativize(Paths.get(epubFilePath));// epub file abs path -->
																				// E:\CDAC\Project\spring_boot_backend_template\
																				// uploaded_files\books\epub_1707245561300.epub

		// Path java.nio.file.Path.relativize(Path other)-->Constructs a relative path
		// between this path and a given path
		// Compare the this path with given path and return a relative path
		System.out.println("Relative path Path : " + relativeEpubPath.toString());// uploaded_files\books\epub_1707245451821.epub
		Path relativeCoverImagePath = baseDirPath.relativize(Paths.get(coverImagePath));

		User u = userRepo.findById(ebook.getUserId()).orElseThrow();

		Ebook b = new Ebook(u, ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(),
				relativeEpubPath.toString(), relativeCoverImagePath.toString());
		b.setAddedOn(new Timestamp(Instant.now().getEpochSecond() * 1000));
		bookRepo.save(b);

		return ResponseEntity.ok("Book uploaded successfully.");
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getAllBooks() throws IOException {
		return getAllBooksInternal(bookRepo.findAll());
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getAllBooksGenre(Genre genre) {
		return getAllBooksInternal(bookRepo.getAllByGenre(genre));
	}

	private ResponseEntity<List<GetAllEbookDto>> getAllBooksInternal(List<Ebook> books) {
		if (books != null) {
			List<GetAllEbookDto> dtos = books.stream().map(this::convertToDtoWithContentforGetAllBook)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	private ResponseEntity<List<GetAllEbookDto>> getAllBooksInternalRejected(List<Object[]> books) {
		if (books != null) {
			List<GetAllEbookDto> dtos = books.stream().map(this::convertToDtoWithContentforGetAllBookRejected)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	private GetAllEbookDto convertToDtoWithContentforGetAllBookRejected(Object [] arr) {
		try {
		byte[] coverImageContent = FileUtils.readFileToByteArray(new File(((Ebook)arr[0]).getImagePath()));
		Ebook ebook = (Ebook)arr[0];
		Rejected rejected = (Rejected)arr[1];
		return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
				ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
				ebook.getProcessedBy().getId(), ebook.getProcessedOn(),coverImageContent, rejected.getComment(), ebook.getAddedOn(), ebook.getRevenue(), ebook.getRating());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found");
		}
	}

	private GetAllEbookDto convertToDtoWithContentforGetAllBook(Ebook ebook) {
		try {
			List<Rating> list = ratingRepo.findByEbookId(ebook.getId());
			ebook.setRating(list.stream().mapToDouble(Rating::getRating).average().orElse(0.0));
		byte[] coverImageContent = FileUtils.readFileToByteArray(new File(ebook.getImagePath()));
			if (ebook.getStatus() != Status.PENDING)
				return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
						ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
						ebook.getProcessedBy().getId(), ebook.getProcessedOn(),coverImageContent, null, ebook.getAddedOn(), ebook.getRevenue(), ebook.getRating());
			else
				return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
						ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
						coverImageContent, ebook.getAddedOn(), ebook.getRevenue());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
		}
	}
	@Override
	public ResponseEntity<GetEbookDto> getByBookId(Long id) {
		Optional<Ebook> ebookOptional = bookRepo.findById(id);

		if (ebookOptional.isPresent()) {
			Ebook ebook = ebookOptional.get();
			GetEbookDto ebookDto = convertToDtoWithContent(ebook);

			return ResponseEntity.ok(ebookDto);
		} else {
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + id);
		}
	}
	
	private GetEbookDto convertToDtoWithContent(Ebook ebook) {
		try {
			byte[] coverImageContent = FileUtils.readFileToByteArray(new File(ebook.getImagePath()));

			return new GetEbookDto(ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(),
					ebook.getFilePath(), coverImageContent);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
		}
	}
	
	@Override
	public ResponseEntity<List<GetAllEbookDto>> getApprovedBookByUserId(Long userId) {
		User u=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		return getAllBooksInternal(bookRepo.findByUserAndStatus(u, Status.APPROVED));
	}
	@Override
	public ResponseEntity<?> getRejectedBookByUserId(Long userId) {
		User u=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		return getAllBooksInternalRejected(bookRepo.findByUserRejected(userId));
	}
	
	@Override
	public ResponseEntity<List<GetAllEbookDto>> getPendingBookByUserId(Long userId) {
		User u=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		return getAllBooksInternal(bookRepo.findByUserAndStatus(u, Status.PENDING));
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getAllRejectedBooks() {

		return getAllBooksInternal(bookRepo.findByStatus(Status.REJECTED));
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getAllApprovedBooks() {
		System.out.println(bookRepo.findByStatus(Status.APPROVED));
		return getAllBooksInternal(bookRepo.findByStatus(Status.APPROVED));
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getAllPendingBooks() {
		System.out.println(bookRepo.findByStatus(Status.PENDING));
		return getAllBooksInternal(bookRepo.findByStatus(Status.PENDING));
	}
	
	@Override
	public ResponseEntity<?> getPurchasedBooks(Long userId) {
		return ResponseEntity.ok(getAllBooksInternal(transRepo.findBooksByUserId(userId)));
	}

	@Override
	public ResponseEntity<?> getOwnPendingBooks(Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		try
		{
			return ResponseEntity.ok(bookRepo.findByUserAndStatus(u, Status.APPROVED)
					.stream()
					.map(book->book.getId())
					.collect(Collectors.toList()));
		}catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Override
	public ResponseEntity<?> deleteBook(Long bookId) {
		Ebook book = bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
		book.setStatus(Status.REMOVED);
		return ResponseEntity.ok("Book delete successfully.");
	}

	@Override
	public ResponseEntity<String> updateBook(EbookDto ebook) throws IOException {
	    if (!ebook.getEpubFile().getOriginalFilename().endsWith(".epub")) {
	        return ResponseEntity.badRequest().body("Ebook to upload must be a .epub file.");
	    }

	    Ebook existingBook = bookRepo.findById(ebook.getBookId()).orElseThrow(() -> new ResourceNotFoundException("Book not found"));

	    String currentWorkingDir = System.getProperty("user.dir");
	    String uploadDir = "src/main/resources/static";
	    File bookDir = new File(currentWorkingDir, uploadDir + File.separator + "books");
	    File imageDir = new File(currentWorkingDir, uploadDir + File.separator + "cover_images");
	    if (!bookDir.exists() && !bookDir.mkdirs()) {
	        return ResponseEntity.status(500).body("Failed to create book directory.");
	    }
	    if (!imageDir.exists() && !imageDir.mkdirs()) {
	        return ResponseEntity.status(500).body("Failed to create image directory.");
	    }

	    String epubFileName = "epub_" + System.currentTimeMillis() + ".epub";
	    String epubFilePath = new File(bookDir, epubFileName).getAbsolutePath();
	    System.out.println("Epub file path : " + epubFilePath.toString());
	    ebook.getEpubFile().transferTo(new File(epubFilePath));

	    String coverImageName = "cover_" + System.currentTimeMillis() + ".jpg";
	    String coverImagePath = new File(imageDir, coverImageName).getAbsolutePath();
	    ebook.getCoverImage().transferTo(new File(coverImagePath));

	    Path baseDirPath = Paths.get(currentWorkingDir);
	    Path relativeEpubPath = baseDirPath.relativize(Paths.get(epubFilePath));
	    Path relativeCoverImagePath = baseDirPath.relativize(Paths.get(coverImagePath));

	    existingBook.setTitle(ebook.getTitle());
	    existingBook.setGenre(ebook.getGenre());
	    existingBook.setDescription(ebook.getDescription());
	    existingBook.setPrice(ebook.getPrice());
	    existingBook.setFilePath(relativeEpubPath.toString());
	    existingBook.setImagePath(relativeCoverImagePath.toString());
	    existingBook.setAddedOn(new Timestamp(Instant.now().getEpochSecond() * 1000));
	    existingBook.setStatus(Status.PENDING);
	    bookRepo.save(existingBook);
	    return ResponseEntity.ok("Book updated successfully.");
	}

}
