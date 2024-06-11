package com.ftn.sbnz.service.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JWTTokenServiceImpl implements IJWTTokenService {
	
	@Autowired
	private JWTTokenRepository tokenRepo;
	
	@Override
	public void createToken(String token) {
		JWTToken jwt = new JWTToken(token);
		this.tokenRepo.save(jwt);
		this.tokenRepo.flush();
	}
	
	@Override
	public void createNoMFAToken(String token) {
		JWTToken jwt = new JWTToken(token, true, true);
		this.tokenRepo.save(jwt);
		this.tokenRepo.flush();
	}
	
	@Override
	public JWTToken findByToken(String token) {
		return this.tokenRepo.findByToken(token).orElse(null);
	}
	
	@Override
	public void invalidateToken(String token) {
		JWTToken jwt = this.tokenRepo.findByToken(token).orElse(null);
		
		if (jwt != null) {
			jwt.setValid(false);
			jwt.setVerified(true);
			this.tokenRepo.save(jwt);
			this.tokenRepo.flush();
		}
	}
	
	@Override
	public boolean isValid(String token) {
		JWTToken jwt = this.tokenRepo.findByToken(token).orElse(null);
		System.out.println("TOKEN NADJEN" + jwt);
		System.out.println("TOKEN VALID" + jwt.isValid()); 
		System.out.println("TOKEN VERIFIED" + jwt.isVerified());
		if (jwt != null && jwt.isValid() && jwt.isVerified()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void verifyToken(String token) {
		JWTToken jwt = this.tokenRepo.findByToken(token).orElse(null);
		if (jwt != null) {
			jwt.setValid(true);
			jwt.setVerified(true);
			this.tokenRepo.save(jwt);
			this.tokenRepo.flush();
		}
	}
}
