package com.ftn.sbnz.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.enums.EmailNotificationType;
import com.ftn.sbnz.model.events.BookingEmailEvent;
import com.ftn.sbnz.model.events.CustomEmailEvent;
import com.ftn.sbnz.model.events.DiscountEmailEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

@Service
public class MailService implements IMailService {
	
	@Autowired
	SendGrid sendGrid;
	
	@Override
	public void sendBookingEmail(BookingEmailEvent emailNotificationEvent) {
		Email from = new Email("vujadinovic01@gmail.com", "Accomodo");
		String subject = "Hello";
		String toRecieve = emailNotificationEvent.getEmailTo();
		Email to = new Email(toRecieve);
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		mail.setSubject(subject);
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);

		String status = "";
		String body = "";
		if (emailNotificationEvent.getType() == EmailNotificationType.BOOKING_RESCHEDULE) 
			body = "Booking for " + emailNotificationEvent.getListingName() + " should be canceled because of overlap!";
		else {
			if (emailNotificationEvent.getType() == EmailNotificationType.BOOKING_ACCEPTED) 
				status = "ACCEPTED";
			else if (emailNotificationEvent.getType() == EmailNotificationType.BOOKING_DENIED) 
				status = "DENIED";
			body = "Booking for " + emailNotificationEvent.getListingName() + " has been " + status + "!";
			if (emailNotificationEvent.getType() == EmailNotificationType.BOOKING_DENIED) 
				body += "Reason: " + emailNotificationEvent.getReason();
		}
		
	    personalization.addDynamicTemplateData("body", body);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("");
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println("MAILLLLLLLLLLLL");
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendDiscountEmail(DiscountEmailEvent emailNotificationEvent) {
		Email from = new Email("vujadinovic01@gmail.com", "Accomodo");
		String subject = "Hello";
		String toRecieve = emailNotificationEvent.getEmailTo();
		Email to = new Email(toRecieve);
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		mail.setSubject(subject);
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);

		String body = emailNotificationEvent.getDiscountDetails();
		System.out.println(body);

	    personalization.addDynamicTemplateData("body", body);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("");
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println("MAILLLLLLLLLLLL");
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendCustomEmail(CustomEmailEvent emailNotificationEvent) {
		Email from = new Email("vujadinovic01@gmail.com", "Accomodo");
		String subject = "Hello";
		String toRecieve = emailNotificationEvent.getEmailTo();
		toRecieve = "vujadinovic01@gmail.com";
		Email to = new Email(toRecieve);
		Content c = new Content("text/plain", "message");
		Mail mail = new Mail(from, subject, to, c);
		mail.setSubject(subject);
		
		Personalization personalization = new Personalization();
	    personalization.addTo(to);

		String body = emailNotificationEvent.getBody();
	    personalization.addDynamicTemplateData("body", body);
	    mail.addPersonalization(personalization);
		mail.setTemplateId("");
		
		Request req = new Request();
		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			Response res = this.sendGrid.api(req);
			System.out.println("MAILLLLLLLLLLLL");
			System.out.println(res.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

