package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.models.Listing;

@Role(Role.Type.EVENT)
@Expires("2m")
public class AddedListingEvent {
    private Listing listing;

    public AddedListingEvent(Listing listing) {
        this.listing = listing;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }
}
