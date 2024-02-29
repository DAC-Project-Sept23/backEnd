package com.app.services;
import org.springframework.http.ResponseEntity;
import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;

public interface UserService {
	ResponseEntity<?> userSignup(UserDto user);
	ResponseEntity<UserDto> getUserByUserId(Long userId) ;
	ResponseEntity<String> setNewPass(ChangePassDto passChange);
	String updateUser(UserUpdateDto user);
	Long findUserId(String userName);
	ResponseEntity<?> setNewPasswordAfterForget(ChangePassDto passChange);
}
