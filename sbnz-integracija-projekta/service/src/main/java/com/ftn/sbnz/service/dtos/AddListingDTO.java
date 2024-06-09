package com.ftn.sbnz.service.dtos;

public class AddListingDTO {
    private Long id;
    private String title;
    private double price;
    private String description;
    private ListingDestinationDTO destination;
    private ListingLocationDTO location;
    private String image;

    
    public AddListingDTO(String title, double price, String description, ListingDestinationDTO destination,
            ListingLocationDTO location, String image) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.destination = destination;
        this.location = location;
        this.image = image;
    }

    public AddListingDTO(Long id, String title, double price, String description, ListingDestinationDTO destination,
            ListingLocationDTO location, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.destination = destination;
        this.location = location;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ListingDestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(ListingDestinationDTO destination) {
        this.destination = destination;
    }

    public ListingLocationDTO getLocation() {
        return location;
    }

    public void setLocation(ListingLocationDTO location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    
}
