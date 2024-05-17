package com.ftn.sbnz.service.dtos;

public class GetListingDTO {
    private Long userId;
    private Long listingId;

    public GetListingDTO(Long userId, Long listingId) {
        this.userId = userId;
        this.listingId = listingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

}
