package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

import com.ftn.sbnz.model.models.Review;

@Role(Role.Type.EVENT)
public class NegativeReviewEvent {
    private Review review;

    public NegativeReviewEvent(Review review) {
        this.review = review;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

}
