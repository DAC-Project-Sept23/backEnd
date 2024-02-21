package com.app.services;

import org.springframework.http.ResponseEntity;

public interface WishlistService {
	ResponseEntity<?> wishlistBook(Long bookId, Long userId);
	ResponseEntity<?> getWishlistByUserId(Long userId);
	ResponseEntity<?> deleteBookFromWishlist(Long bookId, Long userId);
	ResponseEntity<?> getBareWishlistByUserId(Long userId);
}
