package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;

import com.ftn.sbnz.model.enums.TravelerLevel;
import com.ftn.sbnz.model.enums.UserRole;

public class TravelerDetailsDTO {

    private String email;
    private String name;
    private String lastname;
    private TravelerLevel level;

    public TravelerDetailsDTO(String email, String name, String lastname, TravelerLevel level) {
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.level = level;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public TravelerLevel getLevel() {
        return level;
    }
    public void setLevel(TravelerLevel level) {
        this.level = level;
    }
    
    
}
