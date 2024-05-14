package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.UserRole;

public class Owner extends User {
    private boolean isIrresponsible;

    public Owner(String email, String password, String name, String lastname, UserRole role, boolean isIrresponsible) {
        super(email, password, name, lastname, role);
        this.isIrresponsible = isIrresponsible;
    }

    public boolean isIrresponsible() {
        return isIrresponsible;
    }

    public void setIrresponsible(boolean isIrresponsible) {
        this.isIrresponsible = isIrresponsible;
    }

    @Override
    public String toString() {
        return "Owner [isIrresponsible=" + isIrresponsible + "]";
    }

}
