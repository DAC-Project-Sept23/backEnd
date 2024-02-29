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
	@Query("SELECT e, r FROM Ebook e LEFT JOIN FETCH Rejected r ON e.id = r.ebook.id " +
		       "WHERE e.user.id = :userId AND e.status = 'REJECTED' AND r.timestamp = (SELECT MAX(r2.timestamp) FROM Rejected r2 WHERE r2.ebook.id = e.id)")
	List<Object[]> findByUserRejected(Long userId);
	@Query("SELECT e, r FROM Ebook e LEFT JOIN FETCH Rejected r ON e.id = r.ebook.id " +
		       "WHERE e.processedBy.id = :adminId AND e.status IN ('REJECTED', 'REMOVED') AND r.timestamp = (SELECT MAX(r2.timestamp) FROM Rejected r2 WHERE r2.ebook.id = e.id)")
	List<Object[]> findByAdminRejected(Long adminId);
	
}
