package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("2m")
public class CollectReviewableListingsEvent {
    private Long travelerId;

    public CollectReviewableListingsEvent() {
    }

    public CollectReviewableListingsEvent(Long travelerId) {
        this.travelerId = travelerId;
    }

    public Long getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }

}
