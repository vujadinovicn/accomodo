package com.ftn.sbnz.service.dtos;

public class ListingDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private Long ownerId;
    private Long locationId;

    public ListingDTO(Long id, String title, String description, double price, Long ownerId, Long locationId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.ownerId = ownerId;
        this.locationId = locationId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public Long getLocationId() {
        return locationId;
    }
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
