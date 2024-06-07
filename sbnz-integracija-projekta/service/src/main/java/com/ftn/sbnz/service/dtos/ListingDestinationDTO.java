package com.ftn.sbnz.service.dtos;

public class ListingDestinationDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListingDestinationDTO(String name) {
        this.name = name;
    }
}
