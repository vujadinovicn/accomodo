package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.Collection;

import com.ftn.sbnz.model.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Owner extends User {
    private boolean isIrresponsible;

    @ManyToMany(cascade = {}, fetch = FetchType.EAGER, mappedBy="owner")
    private Collection<Listing> favoriteListings = new ArrayList<Listing>();

    public Owner(String email, String password, String name, String lastname) {
        super(email, password, name, lastname, UserRole.OWNER);
        this.isIrresponsible = false;
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
