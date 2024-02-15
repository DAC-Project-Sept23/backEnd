package com.app.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.dto.GetAllEbookDto;
import com.app.dto.ProcessDto;
import com.app.entities.Status;

public interface AdminService {
	
	public ResponseEntity<List<GetAllEbookDto>> getRejectedBookByAdminId(Long userId);
	public ResponseEntity<List<GetAllEbookDto>> getApprovedBookByAdminId(Long userId);
	ResponseEntity<String> processBook(ProcessDto dto);
}
