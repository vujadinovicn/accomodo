package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.CancelBookingNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelBookingNoticeRepository extends JpaRepository<CancelBookingNotice, Long> {
}

