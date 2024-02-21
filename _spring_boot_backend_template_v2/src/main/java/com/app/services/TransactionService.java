package com.app.services;

import org.springframework.http.ResponseEntity;

import com.app.dto.TransactionDto;

public interface TransactionService {
	ResponseEntity<?> postTransaction(TransactionDto dto);
	ResponseEntity<?> getBooksByUser(Long userId);
}
