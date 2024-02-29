package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TransactionDto;
import com.app.services.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@PostMapping
	ResponseEntity<?> postTransaction(@RequestBody TransactionDto dto)
	{
		return transactionService.postTransaction(dto);
	}
	@GetMapping("/{userId}")
	ResponseEntity<?> getBooksByUser(@PathVariable Long userId)
	{
		return transactionService.getBooksByUser(userId);
	}
	
}
