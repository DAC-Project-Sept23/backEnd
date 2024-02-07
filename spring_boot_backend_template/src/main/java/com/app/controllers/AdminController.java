package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.admDto;

import com.app.services.AdminService;



@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminctrl;
	
	@PostMapping("/aprove/{UserId}")
	public String ApproveBook(@PathVariable Long UserId,@RequestBody admDto adm) {
		
		String str=adminctrl.approveBook(UserId,adm.getBookId(),adm.getSts());
		return str;
	}
	
}
