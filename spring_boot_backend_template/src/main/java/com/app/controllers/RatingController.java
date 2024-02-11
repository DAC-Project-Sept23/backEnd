package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.RatingDto;
import com.app.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	BookService bookService;
	
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
