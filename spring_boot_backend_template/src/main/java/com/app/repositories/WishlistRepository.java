package com.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Ebook;
import com.app.entities.User;
import com.app.entities.Wishlist;
import com.app.entities.WishlistId;



public interface WishlistRepository// extends JpaRepository<Wishlist,WishlistId> 
{

	@Query("SELECT w.id.Ebook FROM Wishlist w WHERE w.id.User = :user")
    List<Ebook> findBooksByUserId(@Param("user") User user);
	
}
