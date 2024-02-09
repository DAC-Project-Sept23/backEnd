package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.GetAllEbookDto;
import com.app.dto.admDto;

import com.app.services.AdminService;



@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminctrl;
	
	@PostMapping("/process/{UserId}")
	public String ApproveBook(@PathVariable Long UserId,@RequestBody admDto adm) {
		
		String str=adminctrl.approveBook(UserId,adm.getBookId(),adm.getSts());
		return str;
	}
	

	@GetMapping("/rejected/{UserId}")
	public ResponseEntity<List<GetAllEbookDto>> getAdminRejectedBooks(@PathVariable Long UserId) {
		
		System.out.println();
		return adminctrl.getRejectedBookByAdminId(UserId);
	}
	
	@GetMapping("/approved/{UserId}")
	public ResponseEntity<List<GetAllEbookDto>> getAdminApprovedBooks(@PathVariable Long UserId) {
		
		System.out.println();
		return adminctrl.getApprovedBookByAdminId(UserId);
	}
	
}