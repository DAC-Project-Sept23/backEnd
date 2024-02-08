package com.app.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.app.dto.GetAllEbookDto;
import com.app.entities.Status;

public interface AdminService {
	
	public String approveBook(Long UserId,Long id,Status sts);
	public ResponseEntity<List<GetAllEbookDto>> getRejectedBookByAdminId(Long userId);
	public ResponseEntity<List<GetAllEbookDto>> getApprovedBookByAdminId(Long userId);
}
