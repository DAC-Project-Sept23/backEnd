package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	@PostMapping("/wish/{userId}")
	public String addBookToWishlist(@PathVariable Long  userId,@RequestBody Long bookId)
	{
		wish.AddBookToWish(userId, bookId);
		
		return wish.AddBookToWish(userId, bookId);
	}
}
