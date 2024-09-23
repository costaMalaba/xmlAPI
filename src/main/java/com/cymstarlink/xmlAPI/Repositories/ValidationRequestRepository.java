package com.cymstarlink.xmlAPI.Repositories;

import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRequestRepository extends JpaRepository<ValidationRequestEntity, Integer> {
}
