//package com.app.filters;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.app.jwt_utils.JwtUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class JWTRequestFilter extends OncePerRequestFilter {
//    @Autowired
//    private JwtUtils utils;
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        log.info("in once per request filter");
//        // get authorization header n check if not null n starting with Bearer
//        String header = request.getHeader("Authorization");
//        if (header != null && header.startsWith("Bearer ")) {
//            // Bearer token present --> extract n validate it
//            String token = header.substring(7);
//            System.out.println(token);
//            if (utils.validateJwtToken(token)) {
//            	
//                // valid token --> extract user name from the token
//                String userName = utils.getUserNameFromJwtToken(token);
//                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                	
//                    // load user details from UserDetailsService
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//                    // create Authentication object , wrapping user details lifted from DB
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//                    //set details in auth object
////                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////                  Save this authentication token in the sec ctx.
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    System.out.println(authentication);
//                }
//                else
//                    log.info("user name null or authentication already set , username {}",userName);
//
//            }
//        }
//        else
//        {
//        	log.error("Request header DOES NOT contain a Bearer Token");
//            System.out.println("does not contain bearer token");
//        }
//            
//        //pass the request to the next filter in the chain
//      
//        filterChain.doFilter(request, response);
//
//}}
//
//
//
////package com.app.filters;
////import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.stereotype.Component;
////import org.springframework.web.filter.OncePerRequestFilter;
////
////import com.app.jwt_utils.JwtUtils;
////import com.app.services.CustomUserDetailsService;
////
////import javax.servlet.FilterChain;
////import javax.servlet.ServletException;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////import java.io.IOException;
////
////@Component
////public class JWTRequestFilter extends OncePerRequestFilter {
////
////    private final JwtUtils jwtUtils;
////    private final CustomUserDetailsService userDetailsService;
////
////    public JWTRequestFilter(JwtUtils jwtUtils, CustomUserDetailsService userDetailsService) {
////        this.jwtUtils = jwtUtils;
////        this.userDetailsService = userDetailsService;
////    }
////
////    @Override
////    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
////            throws ServletException, IOException {
////        final String authorizationHeader = request.getHeader("Authorization");
////
////        String username = null;
////        String jwt = null;
////
////        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
////            jwt = authorizationHeader.substring(7);
////            username = jwtUtils.getUserNameFromJwtToken(jwt);
////        }
////
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            var userDetails = userDetailsService.loadUserByUsername(username);
////            if (jwtUtils.validateToken(jwt, userDetails)) {
////                var authenticationToken = new UsernamePasswordAuthenticationToken(
////                        userDetails, null, userDetails.getAuthorities());
////                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////            }
////        }
////
////        filterChain.doFilter(request, response);
////    }
////}
////
//
///*///*
//// * 
//// * //package com.app.filters; // //import java.io.IOException; // //import
//// * javax.servlet.FilterChain; //import javax.servlet.ServletException; //import
//// * javax.servlet.http.HttpServletRequest; //import
//// * javax.servlet.http.HttpServletResponse; // //import
//// * org.springframework.beans.factory.annotation.Autowired; //import
//// * org.springframework.security.authentication.
//// * UsernamePasswordAuthenticationToken; //import
//// * org.springframework.security.core.context.SecurityContextHolder; //import
//// * org.springframework.security.core.userdetails.UserDetails; //import
//// * org.springframework.security.core.userdetails.UserDetailsService; //import
//// * org.springframework.stereotype.Component; //import
//// * org.springframework.web.filter.OncePerRequestFilter; // //import
//// * com.app.jwt_utils.JwtUtils; // //import lombok.extern.slf4j.Slf4j; //
//// * //@Slf4j //@Component //public class JWTRequestFilter extends
//// * OncePerRequestFilter { // @Autowired // private JwtUtils utils; // @Autowired
//// * // private UserDetailsService userDetailsService; // // @Override //
//// * protected void doFilterInternal(HttpServletRequest request,
//// * HttpServletResponse response, FilterChain filterChain) // throws
//// * ServletException, IOException { // log.info("in once per request filter"); //
//// * // get authorization header n check if not null n starting with Bearer //
//// * String header = request.getHeader("Authorization"); // if (header != null &&
//// * header.startsWith("Bearer ")) { // // Bearer token present --> extract n
//// * validate it // String token = header.substring(7); // if
//// * (utils.validateJwtToken(token)) { // // valid token --> extract user name
//// * from the token // String userName = utils.getUserNameFromJwtToken(token); //
//// * // if (userName != null &&
//// * SecurityContextHolder.getContext().getAuthentication() == null) { // // load
//// * user details from UserDetailsService // UserDetails userDetails =
//// * userDetailsService.loadUserByUsername(userName); // // create Authentication
//// * object , wrapping user details lifted from DB //
//// * UsernamePasswordAuthenticationToken authentication = new
//// * UsernamePasswordAuthenticationToken( // userDetails.getUsername(),
//// * userDetails.getPassword(), userDetails.getAuthorities()); // //set details in
//// * auth object // // authentication.setDetails(new
//// * WebAuthenticationDetailsSource().buildDetails(request)); //// Save this
//// * authentication token in the sec ctx. //
//// * SecurityContextHolder.getContext().setAuthentication(authentication); // } //
//// * else // log.info("user name null or authentication already set , username {}"
//// * ,userName); // // } // } else //
//// * log.error("Request header DOES NOT contain a Bearer Token"); // //pass the
//// * request to the next filter in the chain // filterChain.doFilter(request,
//// * response); // // } // //} // //
//// */