package com.app.services;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
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
import com.app.entities.Ebook;
import com.app.entities.Status;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.UserRepository;



@Service
@Transactional
public class AdminServiceImpl implements AdminService{

	
@Autowired
	private BookRepository book;
	
@Autowired
private UserRepository user;

	@Override
	public String approveBook(Long UserId,Long id,Status sts) {
		Ebook book1=book.findById(id).orElse(null);
		User u=user.getReferenceById(UserId);
		
		if(book1==null)
		{
		return "Book Not Exist";	
		}
		book1.setApprovedBy(u);
		book1.setApprovedOn(new Timestamp(Instant.now().getEpochSecond()* 1000));
		book1.setStatus(sts);
		
		if(sts==Status.APPROVED)
		return "Book is Approved";
		else
			return "Book is Rejected";
	}

	@Override
	public ResponseEntity<List<GetAllEbookDto>> getRejectedBookByAdminId(Long userId) {
		User u=user.getReferenceById(userId);
		System.out.println(book.findByApprovedByAndStatus(u,Status.REJECTED));
		return  getAllBooksInternal(book.findByApprovedByAndStatus(u,Status.REJECTED));
		
				}
	@Override
	public ResponseEntity<List<GetAllEbookDto>> getApprovedBookByAdminId(Long userId) {
		User u=user.getReferenceById(userId);
		System.out.println(book.findByApprovedByAndStatus(u,Status.APPROVED));
		return  getAllBooksInternal(book.findByApprovedByAndStatus(u,Status.APPROVED));
		
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
			byte[] coverImageContent = FileUtils.readFileToByteArray(new File(ebook.getImagePath()));
			if(ebook.getStatus()!=Status.PENDING)
			return new GetAllEbookDto(ebook.getUser().getFirstName(),ebook.getUser().getLastName(),ebook.getId(),ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(),ebook.getStatus()
					,ebook.getApprovedBy().getId(),ebook.getApprovedOn(),coverImageContent);
			else
				return new GetAllEbookDto(ebook.getUser().getFirstName(),ebook.getUser().getLastName(),ebook.getId(),ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(),ebook.getStatus()
						,coverImageContent);
		
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
		}
	}

	private GetEbookDto convertToDtoWithContent(Ebook ebook) {
		try {
			byte[] epubFileContent = FileUtils.readFileToByteArray(new File(ebook.getFilePath()));
			byte[] coverImageContent = FileUtils.readFileToByteArray(new File(ebook.getImagePath()));

			return new GetEbookDto(ebook.getTitle(), ebook.getGenre(), ebook.getDescription(), ebook.getPrice(),
					epubFileContent, coverImageContent);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Content not found for ebook with ID: " + ebook.getId());
		}
	}
	

}
