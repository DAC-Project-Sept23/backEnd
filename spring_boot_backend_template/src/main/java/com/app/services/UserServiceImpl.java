package com.app.services;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;
import com.app.entities.Ebook;
import com.app.entities.User;

import com.app.repositories.BookRepository;
import com.app.repositories.UserRepository;
import com.app.repositories.WishlistRepository;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BookRepository bookRepo;
//	@Autowired
//	private WishlistRepository wishRepo;
@Autowired
private ModelMapper mapper;
	@Override
	public ResponseEntity<UserResult> userSignup(UserDto user) {
		User u = new User(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getPassword(),
				user.getDob());
		
		
		if (u != null) {
			userRepo.save(u);
			UserResult result = new UserResult(u, true, "SignUp Successful");
			return new ResponseEntity<UserResult>(result, HttpStatus.CREATED);
		}
		String errorMessage = "Failed to sign up. Please try again.";
		UserResult userResult = new UserResult(null, false, errorMessage);
		return new ResponseEntity<>(userResult, HttpStatus.UNAUTHORIZED);
	}

	@Override
	public ResponseEntity<UserDto> getUserByUserId(Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with user id "+userId+" not exist"));
		
		return ResponseEntity.ok(mapper.map(u,UserDto.class));
	}

	@Override
	public ResponseEntity<String> setNewPass(ChangePassDto passChange) {
		User user=userRepo.findById(passChange.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		if(user.getPassword().equals(passChange.getOldPass()))
		{
			user.setPassword(passChange.getNewPass());
			return ResponseEntity.ok("Password updated successfully");
		}
		else
		 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
	}

	@Override
	public String updateUser(UserUpdateDto user) {
		User u=userRepo.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		
		ModelMapper maper=new ModelMapper();
		maper.map(user,u);
		return "User Updated";
	}

//	@Override
//	public ResponseEntity<String> addWishlist(Long bookId, Long userId) {
//	User u=	userRepo.getReferenceById(userId);
//	Ebook b=bookRepo.getReferenceById(bookId);
//	
//	WishlistId id=new WishlistId();
//	id.setBook(b);
//	id.setUser(u);
//	Wishlist wish=new Wishlist();
//	wish.setId(id);
//	wishRepo.save(wish);
//	return ResponseEntity.ok("Book Added To Wishlist");
//	}

//	@Override
//	public ResponseEntity<List<Ebook>> findBookByUserId(Long userId) {
//		User u=userRepo.getReferenceById(userId);
//	System.out.println(wishRepo.findBooksByUserId(u));
//		return null;//wishRepo.findBooksByUserId(userId) ;
//	}

}