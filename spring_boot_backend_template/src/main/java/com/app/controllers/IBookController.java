package com.app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.dto.EbookDto;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.entities.Genre;

public interface IBookController {

	public ResponseEntity<String> uploadBook(@ModelAttribute("epubFile") EbookDto ebookDto);
	
	public ResponseEntity<List<GetAllEbookDto>> getAllBooks();
	
	public ResponseEntity<List<GetAllEbookDto>> getAllBooksByGenre(@RequestParam Genre genre);
	
	public ResponseEntity<GetEbookDto> getByBookId(@PathVariable Long id);
	
	public ResponseEntity<List<GetAllEbookDto>> getBooksByUserId(@PathVariable Long userId) ;
	
	public ResponseEntity<List<GetAllEbookDto>> getAllNonApprovedBooks();
	
	public ResponseEntity<List<GetAllEbookDto>> getAllApprovedBooks() ;
	
	
	
}
