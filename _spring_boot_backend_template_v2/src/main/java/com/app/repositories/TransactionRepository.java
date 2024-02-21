package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Ebook;
import com.app.entities.Transaction;
import com.app.entities.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByUser(User u);
	List<Transaction> findByUserAndStatus(User u, boolean status);
	@Query("SELECT t.book FROM Transaction t WHERE t.user.id = :userId and t.status = true")
	List<Ebook> findBooksByUserId(Long userId);
	Optional<Transaction> findBySecretkey(String key);
}
