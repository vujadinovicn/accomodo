package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class LocationBackward {
    @Position(0)
    private String subLocation;

    @Position(1)
    private String parentLocation;

    public LocationBackward(String subLocation, String parentLocation) {
        this.subLocation = subLocation;
        this.parentLocation = parentLocation;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
    }

    public String getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(String parentLocation) {
        this.parentLocation = parentLocation;
    }
}
