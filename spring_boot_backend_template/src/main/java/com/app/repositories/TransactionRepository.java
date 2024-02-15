package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Ebook;
import com.app.entities.Transaction;
import com.app.entities.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByUser(User u);
	@Query("SELECT t.book FROM Transaction t WHERE t.user.id = :userId")
	List<Ebook> findBooksByUserId(Long userId);
}
