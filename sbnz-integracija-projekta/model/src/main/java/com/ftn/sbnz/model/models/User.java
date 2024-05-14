package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.UserRole;

public class User {
    private String email;
    private String password;
    private String name;
    private String lastname;
    private UserRole role;
    private boolean isBlocked;

    public User(String email, String password, String name, String lastname, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.isBlocked = false;
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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", password=" + password + ", name=" + name + ", lastname=" + lastname
                + ", role=" + role + ", isBlocked=" + isBlocked + "]";
    }

}
