package com.ftn.sbnz.service.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.ftn.sbnz.service.security.auth.RestAuthenticationEntryPoint;
import com.ftn.sbnz.service.security.auth.TokenAuthenticationFilter;
import com.ftn.sbnz.service.security.jwt.IJWTTokenService;
import com.ftn.sbnz.service.security.jwt.TokenUtils;
// import com.ftn.sbnz.service.security.services.UserServiceImpl;
import com.ftn.sbnz.service.services.UserService;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {
	
	@Autowired
	private IJWTTokenService tokenService;

	@Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  	
  
 	@Bean
 	public DaoAuthenticationProvider authenticationProvider() {
 	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 	  
 	    authProvider.setUserDetailsService(userDetailsService());
 	    authProvider.setPasswordEncoder(passwordEncoder());
 	 
 	    return authProvider;
 	}
 	

 	@Autowired
 	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
 
 	
 	@Bean
 	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
 	    return authConfig.getAuthenticationManager();
 	}
 	
	@Autowired
	private TokenUtils tokenUtils;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

    	http.authorizeRequests()
			// .requestMatchers(myRequestMatcher("/api/**")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/login")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user")).permitAll()
			.requestMatchers(myRequestMatcher("/api/oauth/callback")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/send/verification/email/{email}")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/activate/{activationId}")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/reset/password/email/{email}")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/resetPassword")).permitAll()
			.requestMatchers(myRequestMatcher("api/certificate/validate-upload")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/rotatePassword")).permitAll()
			.requestMatchers(myRequestMatcher("/api/user/**")).permitAll()
			// .requestMatchers(myRequestMatcher("/api/**")).permitAll()
			.anyRequest().authenticated().and()
			.cors().and()
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService(), tokenService), BasicAuthenticationFilter.class);
		
		http.csrf().disable(); 

		http.headers().frameOptions().disable();
        http.authenticationProvider(authenticationProvider());
        http.headers()
        .xssProtection()
        .and()
        .contentSecurityPolicy("script-src 'self'");
        return http.build();
    }

           
    // metoda u kojoj se definisu putanje za igorisanje autentifikacije
    @Bean           
    public WebSecurityCustomizer webSecurityCustomizer() {     
    	// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
    	return (web) -> web.ignoring().antMatchers(HttpMethod.POST, "/api/user/login").
		antMatchers(HttpMethod.POST, "/api/user").antMatchers(HttpMethod.PUT, "api/listing/login");
    }

	private RequestMatcher myRequestMatcher(String endpoint) {
        // Define your custom request matcher here
        return new AntPathRequestMatcher(endpoint);
    }
}
