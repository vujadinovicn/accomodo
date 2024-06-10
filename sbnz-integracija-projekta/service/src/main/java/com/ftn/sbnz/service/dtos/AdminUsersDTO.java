package com.ftn.sbnz.service.dtos;

public class AdminUsersDTO {
    private String name;
    private String email;
    private boolean isBlocked;
    private boolean iressponsible;
    private boolean malicious;

    public AdminUsersDTO(String name, String email, boolean isBlocked, boolean iressponsible, boolean malicious) {
        this.name = name;
        this.email = email;
        this.isBlocked = isBlocked;
        this.iressponsible = iressponsible;
        this.malicious = malicious;
    }
    
    public boolean isIressponsible() {
        return iressponsible;
    }
    public void setIressponsible(boolean iressponsible) {
        this.iressponsible = iressponsible;
    }
    public boolean isMalicious() {
        return malicious;
    }
    public void setMalicious(boolean malicious) {
        this.malicious = malicious;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isBlocked() {
        return isBlocked;
    }
    public void setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    
}
