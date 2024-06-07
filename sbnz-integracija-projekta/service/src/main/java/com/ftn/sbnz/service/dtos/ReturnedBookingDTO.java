package com.ftn.sbnz.service.dtos;

import java.util.Date;

public class ReturnedBookingDTO {
    private Long bookingId;
    private Long travelerId;
    private Long ownerId;
    private String travelerName;
    private String ownerName;
    private String listingName;
    private String status;
    private Date startDate;
    private Date endDate;


    public ReturnedBookingDTO(){}
    
    public ReturnedBookingDTO(Long bookingId, Long travelerId, Long ownerId, String travelerName, String ownerName,
            String listingName, String status, Date startDate, Date endDate) {
        this.bookingId = bookingId;
        this.travelerId = travelerId;
        this.ownerId = ownerId;
        this.travelerName = travelerName;
        this.ownerName = ownerName;
        this.listingName = listingName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public Long getTravelerId() {
        return travelerId;
    }
    public void setTravelerId(Long travelerId) {
        this.travelerId = travelerId;
    }
    public Long getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    public String getTravelerName() {
        return travelerName;
    }
    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getListingName() {
        return listingName;
    }
    public void setListingName(String listingName) {
        this.listingName = listingName;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    

    
}
