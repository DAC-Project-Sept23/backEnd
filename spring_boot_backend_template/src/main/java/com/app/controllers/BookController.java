package com.app.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.EbookDto;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.dto.RatingDto;
import com.app.dto.RejectedBookDto;
import com.app.entities.Genre;
import com.app.entities.Rating;
import com.app.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	// upload epub---> //String title, Genre genre, String description, double
	// price, MultipartFile epubFile,
	// MultipartFile coverImage
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<String> uploadBook(@ModelAttribute("epubFile") EbookDto ebookDto) {
		System.out.println("in upload book method");
		try {
			System.out.println(ebookDto);
			return bookService.uploadBook(ebookDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Failed to upload book.");
		}
	}

	// Get all books//(String title, Genre genre, String description, double price,
	// byte[] epubFileContent, byte[] coverImageContent)


	@GetMapping("/genre")
	public ResponseEntity<List<GetAllEbookDto>> getAllBooksByGenre(@RequestParam Genre genre) {

		return bookService.getAllBooksGenre(genre);

	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<GetEbookDto> getByBookId(@PathVariable Long id) {

		return bookService.getByBookId(id);

	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<GetAllEbookDto>> getBooksByUserId(@PathVariable Long userId) {

		return bookService.getBookByUserId(userId);

	}
	
	@GetMapping("/rejected")
	public ResponseEntity<List<GetAllEbookDto>> getAllNonApprovedBooks() {

		return bookService.getAllRejectedBooks();

	}
	@GetMapping("/approved")
	public ResponseEntity<List<GetAllEbookDto>> getAllApprovedBooks() {

		return bookService.getAllApprovedBooks();

	}
	@GetMapping("/pending")
	public ResponseEntity<List<GetAllEbookDto>> getAllPendingBooks() {

		return bookService.getAllPendingBooks();

	}
	

	@PostMapping("/rating")
	public ResponseEntity<String> doRating(@RequestBody RatingDto rating) {

		return bookService.doRating(rating);

	}
	
	@PostMapping("/update/rating")
	public ResponseEntity<String> updateRating(@RequestBody RatingDto rating) {

		return doRating(rating);

	}
	
	
	@GetMapping("/rating/{bookid}")
	public ResponseEntity<List<RatingDto>> getAllRating(@PathVariable Long bookid) {

		return bookService.getAllRating(bookid);

	}
	

	
	
	


	

}
