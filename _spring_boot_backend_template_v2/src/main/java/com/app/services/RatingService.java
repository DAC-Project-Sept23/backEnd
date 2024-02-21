package com.app.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.dto.RatingDto;

public interface RatingService {
	ResponseEntity<String> doRating(RatingDto rating);
	ResponseEntity<List<RatingDto>> getAllRating(Long bookId);
	ResponseEntity<?> deleteRating(RatingDto rating);
}
