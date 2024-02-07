package com.app.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repositories.BookRepository;
@Service
@Transactional
public class BookServiceImpl  implements BookService{
@Autowired
private BookRepository bookRepo;

}
