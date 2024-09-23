package com.cymstarlink.xmlAPI.Repositories;

import com.cymstarlink.xmlAPI.entities.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLogEntity, Integer> {
}
