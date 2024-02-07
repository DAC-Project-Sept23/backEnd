package com.app.dto;

import com.app.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class UserResult {
	    private User user;
	    private boolean success;
	    private String errorMessage;
}
