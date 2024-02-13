package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Ebook;
import com.app.entities.Genre;
import com.app.entities.Status;
import com.app.entities.User;



public interface BookRepository extends JpaRepository<Ebook, Long> {
	List<Ebook> getAllByGenre(Genre genre);

	List<Ebook> getAllByUserId(Long userId);
	List<Ebook> findByStatus(Status sts);
	List<Ebook>findByUserAndStatus(User u,Status status);
	List<Ebook> findByProcessedByAndStatus(User processedBy,Status status);
	
	 
}
