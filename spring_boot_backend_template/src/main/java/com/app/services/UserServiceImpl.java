package com.app.services;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.entities.User;
import com.app.repositories.UserRepository;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
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
	public String setNewPass(ChangePassDto passChange) {
		User user=userRepo.findById(passChange.getUserId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		if(user.getPassword().equals(passChange.getOldPass()))
		{
			user.setPassword(passChange.getNewPass());
			return "Password Changed!!";
		}
		else
		return "Old Password is incorrect!!!";
	}

}