package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.CredentialsDTO;
import com.ftn.sbnz.service.dtos.TokenDTO;
import com.ftn.sbnz.service.security.jwt.IJWTTokenService;
import com.ftn.sbnz.service.security.jwt.TokenUtils;
import com.ftn.sbnz.service.services.interfaces.IUserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
	private IUserService userService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private IJWTTokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
    
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> login(@Valid @RequestBody CredentialsDTO credentials) {
		System.out.println(credentials);

		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>("Wrong username or password!", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails user = (UserDetails) authentication.getPrincipal();
		User userFromDb = this.userService.getUserByEmail(credentials.getEmail());
		 
		String jwt = tokenUtils.generateToken(user, userFromDb);
		this.tokenService.createToken(jwt);
		
		return new ResponseEntity<TokenDTO>(new TokenDTO(jwt, jwt), HttpStatus.OK);
	}
}
