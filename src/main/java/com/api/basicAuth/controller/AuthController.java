package com.api.basicAuth.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.basicAuth.entity.User;
import com.api.basicAuth.model.JwtResponse;
import com.api.basicAuth.security.CustomUserDetailService;
import com.api.basicAuth.service.UserService;
import com.api.basicAuth.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {
	private static Logger LOG = LogManager.getLogger(AuthController.class);

	@Autowired
	UserService userService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	JwtUtil jwtUtil;
	

	@Autowired
	private AuthenticationManager authenticationManager;

	// completed
	@PostMapping(value = "/token-generation",produces = "application/json")
	public ResponseEntity<?> tokenGeneration(@RequestBody User user,HttpServletResponse response) throws AuthenticationException 
	{
		LOG.info("in Login User = "+user.getUsername());
		
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication); //check 
        final String token = jwtUtil.generateToken(authentication); 
        response.addHeader("token", token);
       return ResponseEntity.ok(new JwtResponse(token));

    }
	
	
	
	
}
