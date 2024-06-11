package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class CustomEmailEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private String emailTo;
    private String body;

    public CustomEmailEvent(String emailTo, String body) {
        this.emailTo = emailTo;
        this.body = body;
    }
    public String getEmailTo() {
        return emailTo;
    }
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    

}
