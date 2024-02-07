package com.app.services;

import com.app.entities.Status;

public interface AdminService {
	
	public String approveBook(Long UserId,Long id,Status sts);
}
