package com.ftn.sbnz.service.mail;

import com.ftn.sbnz.model.events.BookingEmailEvent;
import com.ftn.sbnz.model.events.CustomEmailEvent;
import com.ftn.sbnz.model.events.DiscountEmailEvent;

public interface IMailService {
	public void sendBookingEmail(BookingEmailEvent emailNotificationEvent);
	public void sendDiscountEmail(DiscountEmailEvent emailNotificationEvent);
	public void sendCustomEmail(CustomEmailEvent emailNotificationEvent);
}