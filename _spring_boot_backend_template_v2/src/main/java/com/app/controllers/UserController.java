package com.app.controllers;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dto.AuthRequest;
import com.app.dto.AuthResp;
import com.app.dto.ChangePassDto;
import com.app.dto.UserDto;
import com.app.dto.UserResult;
import com.app.dto.UserUpdateDto;
//import com.app.jwt_utils.JwtUtils;
//import com.app.services.CustomUserDetailsService;
import com.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/users")
public class UserController{
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
//	
//	@Autowired
//	private JwtUtils utils;
	
	//code for otp based email verification
//	private final Map<String, String> otpMap = new HashMap<>();
//	private final JavaMailSender javaMailSender;
//	@Autowired
//    public UserController(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//	@PostMapping("/send-otp")
//	public ResponseEntity<String> sendOTP(@RequestBody Map<String, String> requestBody) {
//	    String email = requestBody.get("email");
//	    System.out.println(email);
//	    if (!isValidEmail(email)) {
//	        return ResponseEntity.badRequest().body("Invalid email address");
//	    }
//	        String otp = generateOTP();
//	        otpMap.put(email, otp);
//	        sendEmail(email, otp);
//	        return ResponseEntity.ok("OTP sent successfully");
//	    }
//	
		private boolean isValidEmail(String email) {
		    // Basic email format validation
		    return email != null && email.matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
		}

	    @PostMapping("/signup")
	    public ResponseEntity<?> userSignup(@RequestBody UserDto user) {
	    	System.out.println(user);
//	        String email = user.getEmail();
//	        String otpFromUser = user.getOtp();
//
//	        // Verify OTP
//	        if (otpMap.containsKey(email)) {
//	            String otp = otpMap.get(email);
//	            if (otp.equals(otpFromUser)) {
//	                // OTP matched, proceed with signup logic
//	                otpMap.remove(email); // Remove OTP from memory after verification
//	                return userService.userSignup(user);
//	            }
//	        }
	        return userService.userSignup(user);
	       // System.out.println("invalid otp");
	        // OTP not matched or expired
	       // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
	    }

	    private String generateOTP() {
	        Random random = new Random();
	        int otp = 100000 + random.nextInt(900000);
	        return String.valueOf(otp);
	    }
//
//	    private void sendEmail(String email, String otp) {
//	        MimeMessage message = javaMailSender.createMimeMessage();
//	        MimeMessageHelper helper = new MimeMessageHelper(message);
//	        try {
//	            helper.setTo(email);
//	            helper.setSubject("Page Palette: OTP verification");
//	            helper.setText("Your OTP is: " + otp);
//	        } catch (MessagingException e) {
//	            e.printStackTrace();
//	        }
//	        javaMailSender.send(message);
//	    }
	    
//	    //code for forget password
//	    @PostMapping("/forget-password")
//	    public ResponseEntity<?> forgetPassword(@RequestBody Map<String, String> requestBody) {
//	        String email = requestBody.get("email");
//	        if (!isValidEmail(email)) {
//	            return ResponseEntity.badRequest().body("Invalid email address");
//	        }
//
//	        String otp = generateOTP();
//	        otpMap.put(email, otp);
//	        sendEmail(email, otp);
//
//	        return ResponseEntity.ok("OTP sent to your email");
//	    }

//	    @PostMapping("/reset-password")
//	    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> requestBody) {
//	        String email = requestBody.get("email");
//	        String otp = requestBody.get("otp");
//	        String newPassword = requestBody.get("newPassword");
//
//	        if (!isValidEmail(email)) {
//	            return ResponseEntity.badRequest().body("Invalid email address");
//	        }
//
//	        if (!otpMap.containsKey(email) || !otpMap.get(email).equals(otp)) {
//	            return ResponseEntity.badRequest().body("Invalid OTP");
//	        }
//	        ChangePassDto dto = new ChangePassDto();
//	        dto.setEmail(email);
//	        dto.setNewPass(newPassword);
//	        userService.setNewPasswordAfterForget(dto);
//	        otpMap.remove(email);
//	        return ResponseEntity.ok("Password reset successfully");
//	    }
	
//   @PostMapping("/signup")
//   //@PostMapping(value = "/signup", consumes = "form-data")
//	public ResponseEntity<?> userSignup(@RequestBody UserDto user) {
//	   System.out.println(user);
//		return userService.userSignup(user);
//
//	}

//	@PostMapping("/authenticate")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody  AuthRequest request){
//		System.out.println("in sign in " + request);
//
//		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getEmail(),
//				request.getPassword());
//		
//		try {
//			// authenticate the credentials
//			Authentication authenticatedDetails = authenticationManager.authenticate(authToken);
//		//	log.info("auth token again " + authenticatedDetails);
//			// => auth succcess
//			String jwtToken = utils.generateJwtToken(authenticatedDetails);
//			String userName = utils.getUserNameFromJwtToken(jwtToken);
//			String UserRoles = utils.getUserRoleFromJwtToken(jwtToken);
//			String isLoggedIn = "true";
//			
//			Long id = userService.findUserId(userName);
//			
//			return ResponseEntity.ok(new AuthResp("Authorization successful!", jwtToken, UserRoles, id, isLoggedIn));
//		} catch (BadCredentialsException e) { // lab work : replace this by a method in global exc handler
//			// send back err resp code
//			System.out.println("err "+e);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//		}
//	}
   
   @GetMapping("/{userId}")
   public ResponseEntity<UserDto> getUserByUserId(@PathVariable Long userId){
      return userService.getUserByUserId(userId);
   }
   
//   @PutMapping("/password/{id}")
//   public ResponseEntity<String> passwordUpdate(@RequestBody ChangePassDto passupdate,@PathVariable Long id){
//    passupdate.setUserId(id);
//     return userService.setNewPass(passupdate);
//   }
   
   @PutMapping("/details/{userId}")
   public ResponseEntity<String> userUpdate(@RequestBody UserUpdateDto user ,@PathVariable Long userId){
	    user.setId(userId);
	     
	     return ResponseEntity.ok(userService.updateUser(user));
   }
   

}
