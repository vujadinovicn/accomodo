package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class DiscountEmailEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private String emailTo;
    private String listingName;
    private String discountDetails;

    public DiscountEmailEvent(String emailTo, String listingName, String discountDetails) {
        this.emailTo = emailTo;
        this.listingName = listingName;
        this.discountDetails = discountDetails;
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

    public String getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(String discountDetails) {
        this.discountDetails = discountDetails;
    }
    
}
