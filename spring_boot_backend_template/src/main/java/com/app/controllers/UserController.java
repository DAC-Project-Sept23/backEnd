package com.app.controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;
import com.app.entities.Ebook;
import com.app.entities.User;
import com.app.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/users")
public class UserController{
	
	@Autowired
	private UserService userService;
	
   @PostMapping("/signup")
   //@PostMapping(value = "/signup", consumes = "form-data")
	public ResponseEntity<UserResult> userSignup(@RequestBody UserDto user) {
		return userService.userSignup(user);

	}
   @GetMapping("/getUser/{userId}")
   public ResponseEntity<UserDto> getUserByUserId(@PathVariable Long userId){
      return userService.getUserByUserId(userId);
   }
   
   @PostMapping("/update/password/{id}")
   public ResponseEntity<String> passwordUpdate(@RequestBody ChangePassDto passupdate,@PathVariable Long id){
    
    passupdate.setUserId(id);
    
     return userService.setNewPass(passupdate);
   }
   
   @PostMapping("/update/details/{userId}")
   public ResponseEntity<String> userUpdate(@RequestBody UserUpdateDto user ,@PathVariable Long userId){
	    user.setId(userId);
	     
	     return ResponseEntity.ok(userService.updateUser(user));
	   }
   
//   @PutMapping("/Wishlist/add/{userId}")
//   public ResponseEntity<ResponseEntity<String>> addBookToWishlist(@RequestBody Long bookId ,@PathVariable Long userId){
//	   
//	   
//	   
//	     return ResponseEntity.ok(userService.addWishlist(bookId,userId));
//	   }
   
//   @GetMapping("/Wishlist/book/{userId}")
//   public ResponseEntity<ResponseEntity<List<Ebook>>> findBookByUserId(@PathVariable Long userId){
//	   
//	   
//	   
//	     return ResponseEntity.ok(userService.findBookByUserId(userId));
//	   }
   
   
}
