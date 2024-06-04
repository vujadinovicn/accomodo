package com.ftn.sbnz.service.security.auth;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ftn.sbnz.service.security.jwt.IJWTTokenService;
import com.ftn.sbnz.service.security.jwt.TokenUtils;

import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;
	
	private IJWTTokenService tokenService;
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService, IJWTTokenService tokenService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
		this.tokenService = tokenService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		String email;
		System.out.println(request.getRequestURI());
		String authToken = tokenUtils.getToken(request);
		
		try {
	
			if (authToken != null) {
				
				
				if (!request.getRequestURI().contains("twofactor")) {
					if (!tokenService.isValid(authToken))
						throw new ExpiredJwtException(null, null, "Invalid token!");
				}
				email = tokenUtils.getUsernameFromToken(authToken);
				if (email != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					
					if (tokenUtils.validateToken(authToken, userDetails)) {
						TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
						authentication.setToken(authToken);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
			
		} catch (ExpiredJwtException ex) {
			LOGGER.debug("Token expired!");
			throw ex;
		} 
		
		chain.doFilter(request, response);
	}

}