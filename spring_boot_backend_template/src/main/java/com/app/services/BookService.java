package com.app.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import com.app.dto.EbookDto;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.entities.Genre;

public interface BookService {
	ResponseEntity<String> uploadBook(EbookDto ebook)
			throws IOException;
	
	ResponseEntity<List<GetAllEbookDto>> getAllBooks() throws IOException;

	

	
	ResponseEntity<List<GetAllEbookDto>> getAllBooksGenre(Genre genre);

	ResponseEntity<GetEbookDto> getByBookId(Long id);

	ResponseEntity<List<GetAllEbookDto>> getBookByUserId(Long userid);

	ResponseEntity<List<GetAllEbookDto>> getAllNonApprovedBooks();

	ResponseEntity<List<GetAllEbookDto>> getAllApprovedBooks();
}
