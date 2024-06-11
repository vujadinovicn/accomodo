package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class MaliciousTravelerEvent {
    private Long travelerId;

    public Long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }

    public MaliciousTravelerEvent(Long travelerId) {
        this.travelerId = travelerId;
    }

    public MaliciousTravelerEvent() {
    }

}
