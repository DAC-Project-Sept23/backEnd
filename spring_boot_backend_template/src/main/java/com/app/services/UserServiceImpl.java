package com.app.services;



import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;
import com.app.entities.User;
import com.app.repositories.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public ResponseEntity<?> userSignup(UserDto user) 
	{
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		User u = new User();
		u.setDob(user.getDob());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setPassword(encodedPassword);
		u.setRoleFromString(user.getRole());
		u.setEmail(user.getEmail());
		System.out.println(u.toString());
		try {
			userRepo.save(u);
			return ResponseEntity.ok("Registration successful.");
		}catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email id already exists, please login.");
		}
			
	}
	@Override
	public ResponseEntity<UserDto> getUserByUserId(Long userId) {
		User u = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with user id "+userId+" not exist"));
		
		return ResponseEntity.ok(mapper.map(u,UserDto.class));
	}
	@Override
	public ResponseEntity<String> setNewPass(ChangePassDto passChange) {
	    User user = userRepo.findById(passChange.getUserId())
	                         .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

	    if (passwordEncoder.matches(passChange.getOldPass(), user.getPassword())) {
	        if (passwordEncoder.matches(passChange.getNewPass(), user.getPassword())) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                                 .body("New Password can not be same as old password.");
	        }
	        user.setPassword(passwordEncoder.encode(passChange.getNewPass()));
	        userRepo.save(user); // Save the updated user
	        return ResponseEntity.ok("Password updated successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                             .body("Old password is incorrect");
	    }
	}
	@Override
	public String updateUser(UserUpdateDto user) {
		User u=userRepo.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		
		ModelMapper maper=new ModelMapper();
		maper.map(user,u);
		return "User Updated";
	}
	@Override
	public Long findUserId(String userName) {
		User user = userRepo.findByEmail(userName)
				.orElseThrow(()-> new RuntimeException("Invalid Email"));
		
		return user.getId();
	
	}
	@Override
	public ResponseEntity<?> setNewPasswordAfterForget(ChangePassDto passChange) {
		    User user = userRepo.findByEmail(passChange.getEmail())
		                         .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		        user.setPassword(passwordEncoder.encode(passChange.getNewPass()));
		        userRepo.save(user); // Save the updated user
		        return ResponseEntity.ok("Password updated successfully");
		}
}