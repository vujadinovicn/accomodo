package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
public class NewDiscountEvent {
    private Long discountId;

    public NewDiscountEvent() {
    }

    public NewDiscountEvent(Long discountId) {
        this.discountId = discountId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

}
