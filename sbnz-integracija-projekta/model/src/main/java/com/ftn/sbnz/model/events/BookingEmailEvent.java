package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.enums.EmailNotificationType;

@Role(Role.Type.EVENT)
@Expires("2m")
public class BookingEmailEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private String emailTo;
    private String listingName;
    private EmailNotificationType type;
    private String reason;

    public BookingEmailEvent(String emailTo, String listingName, EmailNotificationType type, String reason) {
        this.emailTo = emailTo;
        this.listingName = listingName;
        this.type = type;
        this.reason = reason;
    }
    public String getEmailTo() {
        return emailTo;
    }
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    public String getListingName() {
        return listingName;
    }
    public void setListingName(String listingName) {
        this.listingName = listingName;
    }
    public EmailNotificationType getType() {
        return type;
    }
    public void setType(EmailNotificationType type) {
        this.type = type;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    
}
