package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.RatingDto;
import com.app.services.BookService;
import com.app.services.RatingService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/ratings")
public class RatingController {
	@Autowired
	private RatingService ratingService;
	
	@PostMapping
	public ResponseEntity<String> doRating(@RequestBody RatingDto rating) {

		return ratingService.doRating(rating);

	}
	
	@PutMapping
	public ResponseEntity<String> updateRating(@RequestBody RatingDto rating) {

		return doRating(rating);

	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<List<RatingDto>> getAllRating(@PathVariable Long bookId) {

		return ratingService.getAllRating(bookId);

	}
	@DeleteMapping
	public ResponseEntity<?> deteleRating(@RequestBody RatingDto rating)
	{
		return ratingService.deleteRating(rating);
	}

}
