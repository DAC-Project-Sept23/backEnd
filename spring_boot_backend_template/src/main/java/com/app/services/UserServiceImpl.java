package com.app.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.UserDto;
import com.app.entities.User;
import com.app.repositories.UserRepository;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public User userSignup(UserDto user) {
		User u = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getPassword(),
				user.getDob());
		return userRepo.save(u);
	}

}