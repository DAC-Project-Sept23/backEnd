package com.app.services;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import com.app.dto.EbookDto;
import com.app.dto.GetAllEbookDto;
import com.app.dto.GetEbookDto;
import com.app.dto.RatingDto;
import com.app.dto.RejectedBookDto;
import com.app.entities.Genre;
import com.app.entities.Rating;

public interface BookService {
	ResponseEntity<String> uploadBook(EbookDto ebook)
			throws IOException;
	
	ResponseEntity<List<GetAllEbookDto>> getAllBooks() throws IOException;

	
	 ResponseEntity<List<GetAllEbookDto>> getAllPendingBooks();
	
	ResponseEntity<List<GetAllEbookDto>> getAllBooksGenre(Genre genre);

	ResponseEntity<GetEbookDto> getByBookId(Long id);

	ResponseEntity<List<GetAllEbookDto>> getApprovedBookByUserId(Long userid);

     ResponseEntity<List<GetAllEbookDto>> getAllRejectedBooks();

	ResponseEntity<List<GetAllEbookDto>> getAllApprovedBooks();



	
}
