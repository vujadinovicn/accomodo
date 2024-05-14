package com.ftn.sbnz.model.models;

import com.ftn.sbnz.model.enums.TravelerAgeGroup;
import com.ftn.sbnz.model.enums.TravelerLevel;
import com.ftn.sbnz.model.enums.UserRole;

public class Traveler extends User {
    private boolean isIressponsible;
    private boolean isMalicious;
    private TravelerAgeGroup ageGroup;
    private TravelerLevel level;

    public Traveler(String email, String password, String name, String lastname, UserRole role, boolean isIressponsible,
            boolean isMalicious, TravelerAgeGroup ageGroup, TravelerLevel level) {
        super(email, password, name, lastname, role);
        
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

    @Override
    public String toString() {
        return "Traveler [isIressponsible=" + isIressponsible + ", isMalicious=" + isMalicious + ", ageGroup="
                + ageGroup + ", level=" + level + "]";
    }

}
