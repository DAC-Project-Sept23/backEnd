package com.app.services;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.GetAllEbookDto;
import com.app.entities.Ebook;
import com.app.entities.Status;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Override
	public ResponseEntity<?> wishlistBook(Long bookId, Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		Ebook b = bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
		try {
			u.AddBookToWishList(b);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong, please try again later");
		}
	}
	@Override
	public ResponseEntity<?> getWishlistByUserId(Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		Set<Ebook> wishlist = u.getWishlist();
		wishlist.size();
		List<GetAllEbookDto> dtos = wishlist.stream().map(this::convertToDtoWithContentforGetAllBook)
				.collect(Collectors.toList());
		return ResponseEntity.ok(dtos);
	}
	@Override
	public ResponseEntity<?> deleteBookFromWishlist(Long bookId, Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		Ebook b = bookRepo.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
		try {
			Set<Ebook> wishlist = u.getWishlist();
			wishlist.remove(b);
			return ResponseEntity.ok().build();
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	private GetAllEbookDto convertToDtoWithContentforGetAllBook(Ebook ebook) {
		try {
		byte[] coverImageContent = FileUtils.readFileToByteArray(new File(ebook.getImagePath()));
			if (ebook.getStatus() != Status.PENDING)
				return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
						ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
						ebook.getProcessedBy().getId(), ebook.getProcessedOn(),coverImageContent);
			else
				return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
						ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
						coverImageContent);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
		}
	}
	

}
