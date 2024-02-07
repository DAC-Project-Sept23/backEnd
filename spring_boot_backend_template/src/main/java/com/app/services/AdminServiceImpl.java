package com.app.services;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		return "Book is approved";
	}

}
