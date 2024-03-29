package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.entities.User;
import com.app.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
   @PostMapping("/signup")
	public ResponseEntity<UserResult> userSignup(@RequestBody UserDto user) {
		User u = userService.userSignup(user);
		if (u != null) {
			UserResult result = new UserResult(u, true, "SignUp Successful");
			return new ResponseEntity<UserResult>(result, HttpStatus.CREATED);
		}
		String errorMessage = "Failed to sign up. Please try again.";
		UserResult userResult = new UserResult(null, false, errorMessage);
		return new ResponseEntity<>(userResult, HttpStatus.UNAUTHORIZED);
	}
}
