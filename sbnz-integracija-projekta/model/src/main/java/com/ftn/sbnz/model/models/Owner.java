package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.Collection;

import com.ftn.sbnz.model.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;

@Entity
public class Owner extends User {
    private boolean isIrresponsible;

    @ManyToMany(cascade = {}, fetch = FetchType.EAGER, mappedBy="owner")
    private Collection<Listing> favoriteListings = new ArrayList<Listing>();

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
