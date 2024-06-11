package com.ftn.sbnz.service.dtos;

import java.time.LocalDateTime;

public class ReturnedDiscountDTO {
    private Long id;
    private double amount;
    private LocalDateTime validTo;

    public ReturnedDiscountDTO() {
    }

    public ReturnedDiscountDTO(Long id, double amount, LocalDateTime validTo) {
        this.id = id;
        this.amount = amount;
        this.validTo = validTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
