package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;
import com.ftn.sbnz.model.enums.UserRole;

public class AddUserDTO {

    private String email;
    private String password;
    private String name;
    private String lastname;
    private UserRole role;
    private LocalDateTime dateOfBirth;

    public AddUserDTO() {

    }
    public AddUserDTO(String email, String password, String name, String lastname, UserRole role,
            LocalDateTime dateOfBirth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    

}
