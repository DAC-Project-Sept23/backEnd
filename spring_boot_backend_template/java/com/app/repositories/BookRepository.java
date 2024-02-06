package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Ebook;

public interface BookRepository extends JpaRepository<Ebook, Long> {

}
