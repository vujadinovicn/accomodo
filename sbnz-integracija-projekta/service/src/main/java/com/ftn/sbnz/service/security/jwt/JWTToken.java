package com.ftn.sbnz.service.security.jwt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "jwttokens")
public class JWTToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(max = 500)
	private String token;
	
	private boolean valid;
	
	private boolean verified;

	public JWTToken() {}
	
	public JWTToken(int id, String token, boolean valid) {
		super();
		this.id = id;
		this.token = token;
		this.valid = valid;
	}
	
	public JWTToken(String token) {
		this.token = token;
		this.valid = false;
		this.verified = false;
	}
	
	public JWTToken(String token, Boolean valid, Boolean verified) {
		this.token = token;
		this.valid = valid;
		this.verified = verified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
}
