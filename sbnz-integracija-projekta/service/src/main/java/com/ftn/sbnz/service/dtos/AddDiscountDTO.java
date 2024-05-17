package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;

public class AddDiscountDTO {
    private Long listingId;
    private Long ownerId;
    private double amount;
    private LocalDateTime validTo;

    public AddDiscountDTO(Long listingId, Long ownerId, double amount, LocalDateTime validTo) {
        this.listingId = listingId;
        this.ownerId = ownerId;
        this.amount = amount;
        this.validTo = validTo;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

}
