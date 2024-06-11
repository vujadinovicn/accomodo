package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.Discount;
import com.google.common.base.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByListingId(Long id);
}
