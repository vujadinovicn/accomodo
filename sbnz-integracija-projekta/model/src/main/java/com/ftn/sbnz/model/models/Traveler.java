package com.ftn.sbnz.model.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.ftn.sbnz.model.enums.TravelerAgeGroup;
import com.ftn.sbnz.model.enums.TravelerLevel;
import com.ftn.sbnz.model.enums.UserRole;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity
public class Traveler extends User {
    private boolean isIressponsible;
    private boolean isMalicious;
    private TravelerAgeGroup ageGroup;
    private TravelerLevel level;
    private Date dateOfIrresponsible;

    @ManyToMany(cascade = {}, fetch = FetchType.EAGER)
    private Collection<Listing> favoriteListings = new ArrayList<Listing>();
    @ManyToMany(cascade = {}, fetch = FetchType.LAZY)
    private Collection<Destination> favoriteDestinations = new ArrayList<Destination>();

    public Traveler(){
        super();
    }
    public Traveler(String email, String password, String name, String lastname, LocalDateTime dateOfBirth) {
        super(email, password, name, lastname, UserRole.TRAVELER, dateOfBirth);
        this.isIressponsible = false;
        this.isMalicious = false;
        this.ageGroup = TravelerAgeGroup.YOUNG_ADULT;
        this.level = TravelerLevel.NONE;
    }

    public Traveler(String email, String password, String name, String lastname, UserRole role, boolean isIressponsible,
            boolean isMalicious, TravelerAgeGroup ageGroup, TravelerLevel level, LocalDateTime dateOfBirth) {
        super(email, password, name, lastname, role, dateOfBirth);

        this.isIressponsible = isIressponsible;
        this.isMalicious = isMalicious;
        this.ageGroup = ageGroup;
        this.level = level;
    }

    public boolean isIressponsible() {
        return isIressponsible;
    }

    public void setIressponsible(boolean isIressponsible) {
        this.isIressponsible = isIressponsible;
    }

    public boolean isMalicious() {
        return isMalicious;
    }

    public void setMalicious(boolean isMalicious) {
        this.isMalicious = isMalicious;
    }

    public TravelerAgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(TravelerAgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public TravelerLevel getLevel() {
        return level;
    }

    public void setLevel(TravelerLevel level) {
        this.level = level;
    }

    public Collection<Listing> getFavoriteListings() {
        return favoriteListings;
    }

    public void setFavoriteListings(Collection<Listing> favoriteListings) {
        this.favoriteListings = favoriteListings;
    }

    public Collection<Destination> getFavoriteDestinations() {
        return favoriteDestinations;
    }

    public void setFavoriteDestinations(Collection<Destination> favoriteDestinations) {
        this.favoriteDestinations = favoriteDestinations;
    }

    public Date getDateOfIrresponsible() {
        return dateOfIrresponsible;
    }
    public void setDateOfIrresponsible(Date dateOfIrresponsible) {
        this.dateOfIrresponsible = dateOfIrresponsible;
    }

    @Override
    public String toString() {
        return "Traveler [isIressponsible=" + isIressponsible + ", isMalicious=" + isMalicious + ", ageGroup="
                + ageGroup + ", level=" + level + "]";
    }
}
