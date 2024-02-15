package com.app.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;
import com.app.entities.Ebook;
import com.app.entities.User;

public interface UserService {
	ResponseEntity<UserResult> userSignup(UserDto user);
	ResponseEntity<UserDto> getUserByUserId(Long userId) ;
	ResponseEntity<String> setNewPass(ChangePassDto passChange);
	String updateUser(UserUpdateDto user);
	Long findUserId(String userName);
	
}
