package com.app.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Ebook;
import com.app.entities.User;
import com.app.repositories.BookRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private BookRepository brpo;
	
	@Override
	public String AddBookToWish(Long userId, Long bookId) {
		

		User usr=urepo.findById(userId).orElseThrow();
		System.out.println(usr);
		Ebook bk=brpo.findById(bookId).orElseThrow();
		System.out.println(bk);
		
//		usr.getWishlist().add(bk);
//		bk.getWishlistedBy().add(usr);
		//usr.AddBookToWishList(bk);
		//bk.AddUserToWishList(usr);
		
		return "Added";
	}

}
