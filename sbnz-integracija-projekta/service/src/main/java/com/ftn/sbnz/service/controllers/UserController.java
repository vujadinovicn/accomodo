package com.ftn.sbnz.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.ftn.sbnz.model.models.User;
import com.ftn.sbnz.service.dtos.AdminUsersDTO;
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

		if (userFromDb.isBlocked())
			return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
		 
		String jwt = tokenUtils.generateToken(user, userFromDb);
		this.tokenService.createToken(jwt);
		
		return new ResponseEntity<TokenDTO>(new TokenDTO(jwt, jwt), HttpStatus.OK);
	}

	@GetMapping(value = "/admin") 
	public List<AdminUsersDTO> getForAdmin(){
		return this.userService.getForAdmin();
	}

	@PutMapping(value = "/block") 
	public void block(@RequestParam String email){
		this.userService.block(email);
	}

	@PutMapping(value = "/unblock") 
	public void unblock(@RequestParam String email){
		this.userService.unblock(email);
	}
}
