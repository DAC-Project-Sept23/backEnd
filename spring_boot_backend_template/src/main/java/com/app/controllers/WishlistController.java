package com.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Ebook;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.UserRepository;
import com.app.services.BookService;
import com.app.services.UserService;
import com.app.services.WishlistService;



@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RequestMapping("/wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wish;
	
	
	
	@Autowired
	private BookService book;
	
	@PostMapping
	ResponseEntity<?> wishlistBook(@RequestBody Map<String, Long> requestBody)
	  {
			Long bookId = requestBody.get("bookId");
		    Long userId = requestBody.get("userId");
	       if (userId == null || bookId == null || userId <= 0 || bookId <= 0) {
	           return ResponseEntity.badRequest().body("Invalid userId or bookId");
	       }
	       return wish.wishlistBook(bookId, userId);
	   }
	
	@GetMapping("/{userId}")
	ResponseEntity<?> getWishList(@PathVariable Long userId)
	{
		return wish.getWishlistByUserId(userId);
	}
	
	@DeleteMapping
	ResponseEntity<?> deleteFromWishList(@RequestBody Map<String, Long> requestBody)
	{
		Long bookId = requestBody.get("bookId");
	    Long userId = requestBody.get("userId");
       if (userId == null || bookId == null || userId <= 0 || bookId <= 0) {
           return ResponseEntity.badRequest().body("Invalid userId or bookId");
       }
       return wish.deleteBookFromWishlist(bookId, userId);
	}
}
