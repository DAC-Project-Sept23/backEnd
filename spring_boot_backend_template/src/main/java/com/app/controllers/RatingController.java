package com.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.dto.RatingDto;
import com.app.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/rating")
@Validated
public class RatingController {

	@Autowired
	private BookService bookService;

	@PostMapping
	public ResponseEntity<String> doRating(@Valid @RequestBody RatingDto rating) {

		return bookService.doRating(rating);

	}

	@PostMapping("/update")
	public ResponseEntity<String> updateRating(@RequestBody RatingDto rating) {

		return doRating(rating);

	}

	@GetMapping("/{bookid}")
	public ResponseEntity<List<RatingDto>> getAllRating(@PathVariable Long bookid) {

		return bookService.getAllRating(bookid);

	}

	@GetMapping("/delete")
	public ResponseEntity<String> getAllRating(@RequestParam Long userId, @RequestParam Long bookid) {

		return bookService.deleteRatingById(userId, bookid);

	}

}
