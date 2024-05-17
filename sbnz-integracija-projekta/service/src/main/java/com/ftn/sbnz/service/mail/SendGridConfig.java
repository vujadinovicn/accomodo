package com.ftn.sbnz.service.mail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sendgrid.SendGrid;

@Configuration
public class SendGridConfig {
	
	@Value("")
	private String apiKey;
	
	@Bean
	public SendGrid getSendGrid() {
		return new SendGrid(this.apiKey);
	}
}
