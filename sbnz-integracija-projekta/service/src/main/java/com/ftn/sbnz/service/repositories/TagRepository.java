package com.ftn.sbnz.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    
}
