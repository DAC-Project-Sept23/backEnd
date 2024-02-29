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
		Transaction transaction = transRepo.findBySecretkey(dto.getKey()).orElseThrow(()->new ResourceNotFoundException("Invalid transaction"));
		if((transaction.getBook().getId() != b.getId()) || (transaction.getUser().getId() != u.getId()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		transaction.setPurchasedOn(new Timestamp(Instant.now().getEpochSecond() * 1000));
		if(!transaction.getSecretkey().equals(dto.getKey()))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		transaction.setStatus(true);
		try {
			transRepo.save(transaction);
			b.setRevenue(b.getRevenue() + b.getPrice()*0.656);
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
				.body(transRepo.findByUserAndStatus(user, true)
								.stream()
								.map(t->t.getBook().getId())
								.collect(Collectors.toList()));
	}
}
