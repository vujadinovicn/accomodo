package com.ftn.sbnz.service.services.interfaces;


import java.util.List;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;

public interface IListingService {
    public Listing getById(GetListingDTO dto);
    public Listing findById(long id);
    public void addListing(AddListingDTO dto);

    public void addDiscount(AddDiscountDTO dto);
    public void addReview(AddReviewDTO dto);
    public void backward();
    public List<ReturnedListingDTO> getListingsForOwner();
    Review getReview(long listingId, long travelerId);
    public List<Listing> getListingRecommendations(Long id);
    public List<ReturnedListingDTO> getAll();
    
}
