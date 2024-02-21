package com.app.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.controllers.BookController;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.dto.ProcessDto;
import com.app.entities.Ebook;
import com.app.entities.Rejected;
import com.app.entities.Status;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.RejectedBookRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
	private BookRepository book;

	@Autowired
	private UserRepository user;
	@Autowired
	private RejectedBookRepository rejectedBookRepo;

	@Override
	public ResponseEntity<String> processBook(ProcessDto dto) {
		System.out.println("in approve book "+dto);
		Ebook book1 = book.findById(dto.getBookId())
				.orElseThrow(() -> new ResourceNotFoundException("Book with id " + dto.getBookId() + " not found"));
		User admin = user.getReferenceById(dto.getAdminId());

		book1.setProcessedBy(admin);
		book1.setProcessedOn(new Timestamp(Instant.now().getEpochSecond() * 1000));
		book1.setStatus(dto.getStatus());

		if (dto.getStatus() == Status.APPROVED) {
			return ResponseEntity.ok("Book is Approved");

		}
		Rejected rejectedBook = new Rejected(book1.getTitle(),book1.getFilePath(),admin,book1.getUser(),dto.getComment(), book1);
		rejectedBookRepo.save(rejectedBook);
		
		return ResponseEntity.ok("Book is rejected");
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getRejectedBookByAdminId(Long userId) {
		User u = user.getReferenceById(userId);
		return getAllBooksInternalRejected(book.findByAdminRejected(userId));

	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getApprovedBookByAdminId(Long userId) {
		User u = user.getReferenceById(userId);
		System.out.println(book.findByProcessedByAndStatus(u, Status.APPROVED));
		return getAllBooksInternal(book.findByProcessedByAndStatus(u, Status.APPROVED));

	}

	private ResponseEntity<List<GetAllEbookDto>> getAllBooksInternal(List<Ebook> books) {
		if (books != null) {
			List<GetAllEbookDto> dtos = books.stream().map(this::convertToDtoWithContentforGetAllBook)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	private GetAllEbookDto convertToDtoWithContentforGetAllBook(Ebook ebook) {
		try {
				return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
						ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(), ebook.getProcessedOn(), ebook.getAddedOn());
		} catch (Exception e) {
			e.printStackTrace();
		throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
	}
	}
	private ResponseEntity<List<GetAllEbookDto>> getAllBooksInternalRejected(List<Object[]> books) {
		if (books != null) {
			List<GetAllEbookDto> dtos = books.stream().map(this::convertToDtoWithContentforGetAllBookRejected)
					.collect(Collectors.toList());
			return ResponseEntity.ok(dtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	private GetAllEbookDto convertToDtoWithContentforGetAllBookRejected(Object [] arr) {
		try {
		byte[] coverImageContent = FileUtils.readFileToByteArray(new File(((Ebook)arr[0]).getImagePath()));
		Ebook ebook = (Ebook)arr[0];
		Rejected rejected = (Rejected)arr[1];
		return new GetAllEbookDto(ebook.getUser().getFirstName(), ebook.getUser().getLastName(), ebook.getId(),
				ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(), ebook.getStatus(),
				ebook.getProcessedBy().getId(), ebook.getProcessedOn(),coverImageContent, rejected.getComment(), ebook.getAddedOn(), ebook.getRevenue(), ebook.getRating());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found");
		}
	}

}
