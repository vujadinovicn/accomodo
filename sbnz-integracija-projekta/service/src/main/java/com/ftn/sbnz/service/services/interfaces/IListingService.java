package com.ftn.sbnz.service.services.interfaces;


import java.util.List;

import com.ftn.sbnz.model.models.Listing;
import com.ftn.sbnz.model.models.Review;
import com.ftn.sbnz.service.dtos.AddDiscountDTO;
import com.ftn.sbnz.service.dtos.AddListingDTO;
import com.ftn.sbnz.service.dtos.AddReviewDTO;
import com.ftn.sbnz.service.dtos.GetListingDTO;
import com.ftn.sbnz.service.dtos.ReturnedDiscountDTO;
import com.ftn.sbnz.service.dtos.ReturnedListingDTO;
import com.ftn.sbnz.service.dtos.ReturnedReviewDTO;

public interface IListingService {
    public Listing getById(GetListingDTO dto);
    public Listing findById(long id);
    public void addListing(AddListingDTO dto);

    public ReturnedDiscountDTO addDiscount(AddDiscountDTO dto);
    public void addReview(AddReviewDTO dto);

    public List<ReturnedListingDTO> backward(String location);
    public void delete(Long id);
    public List<ReturnedListingDTO> filterByRating(int rating);
    public List<ReturnedListingDTO> filterByPrice(double min, double max);

    public List<ReturnedListingDTO> getListingsForOwner();
    Review getReview(long listingId, long travelerId);
    public List<ReturnedListingDTO> getListingRecommendations(Long id);
    public List<ReturnedListingDTO> getAll();
    public List<ReturnedReviewDTO> getReviews(Long id);
    public void deleteDiscount(Long id);
    
}
