package com.cymstarlink.xmlAPI.Repositories;

import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import com.cymstarlink.xmlAPI.entities.ValidationResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationResponseRepository extends JpaRepository<ValidationResponseEntity, Integer> {
}
