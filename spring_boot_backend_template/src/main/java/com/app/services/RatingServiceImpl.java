package com.app.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.RatingDto;
import com.app.entities.CompositKey;
import com.app.entities.Ebook;
import com.app.entities.Rating;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.RatingRepository;
import com.app.repositories.UserRepository;
@Service
@Transactional
public class RatingServiceImpl implements RatingService {
	@Autowired
	private RatingRepository ratingRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper mapper;
	@Override
	public ResponseEntity<String> doRating(RatingDto rating) {
		User u = userRepo.findById(rating.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User with id" + rating.getUserId() + " not found"));
		Ebook b = bookRepo.findById(rating.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("book with id" + rating.getBookId() + " not found"));
		Rating r = new Rating(new CompositKey(u.getId(), b.getId()), u, b, rating.getComment(), rating.getRating());
		Rating savedRating = ratingRepo.save(r);
		if (savedRating != null) {
			return ResponseEntity.ok("Rating added successfully");
		}
		return ResponseEntity.status(500).body("Failed to add rating");
	}

	@Override
	public ResponseEntity<List<RatingDto>> getAllRating(Long bookId) {
		Ebook b = bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("book with id " + bookId + " not found"));
		List<Rating> list= ratingRepo.findByEbookId(b.getId());
		return ResponseEntity.ok(convertToDtoList(list));
	}
	
	public List<RatingDto> convertToDtoList(List<Rating> ratings) {
        return ratings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
	private RatingDto convertToDto(Rating rating) {
       RatingDto dto= mapper.map(rating, RatingDto.class);
       User u= userRepo.findById(rating.getUser().getId()).orElseThrow();
       dto.setFirstName(u.getFirstName());
       dto.setLastName(u.getLastName());
       dto.setUserId(u.getId());
       return dto;
    }

	@Override
	public ResponseEntity<?> deleteRating(RatingDto rating) {
		try {
			ratingRepo.deleteById(new CompositKey(rating.getUserId(), rating.getBookId()));
			return ResponseEntity.ok().build();
		}
		catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
