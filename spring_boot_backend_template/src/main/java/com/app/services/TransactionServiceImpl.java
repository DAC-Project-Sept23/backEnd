package com.app.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.TransactionDto;
import com.app.entities.Ebook;
import com.app.entities.Transaction;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.TransactionRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BookRepository bookRepo;
	@Override
	public ResponseEntity<?> postTransaction(TransactionDto dto) {
		User u = userRepo.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		Ebook b = bookRepo.findById(dto.getBookId()).orElseThrow(()->new ResourceNotFoundException("Book Not Found"));
		Transaction transaction = new Transaction();
		transaction.setBook(b);
		transaction.setUser(u);
		transaction.setPurchasedOn(new Timestamp(Instant.now().getEpochSecond() * 1000));
		transaction.setPrice(b.getPrice());
		try {
			transRepo.save(transaction);
			u.getWishlist().remove(b);
			return ResponseEntity.ok("Payment processed successfully.");
		}
		catch(Exception e)
		{
			return ResponseEntity.internalServerError().build();
		}
	}
	@Override
	public ResponseEntity<?> getBooksByUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		return ResponseEntity.status(HttpStatus.OK)
				.body(transRepo.findByUser(user)
								.stream()
								.map(t->t.getBook().getId())
								.collect(Collectors.toList()));
	}
}
