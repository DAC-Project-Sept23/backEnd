package com.app.services;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.app.dto.AuthRequest;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.entities.User;

public interface UserService {
	ResponseEntity<UserResult> userSignup(UserDto user);
	ResponseEntity<UserDto> getUserByUserId(Long userId) ;
	User authenticateUser(@Valid AuthRequest request);
	Long findUserId(String userName);
}
