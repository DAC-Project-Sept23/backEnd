package com.app.services;

import com.app.dto.UserDto;
import com.app.entities.User;

public interface UserService {
	User userSignup(UserDto user);
}
