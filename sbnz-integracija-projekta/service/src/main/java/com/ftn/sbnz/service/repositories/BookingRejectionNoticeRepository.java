package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.BookingRejectionNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRejectionNoticeRepository extends JpaRepository<BookingRejectionNotice, Long> {
}
