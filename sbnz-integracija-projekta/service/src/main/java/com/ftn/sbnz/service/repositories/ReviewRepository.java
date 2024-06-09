package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Review;
import com.google.common.base.Optional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByTravelerIdAndListingId(long travelerId, long listingId);
    List<Review> findAllByListingId(long listingId);
}
