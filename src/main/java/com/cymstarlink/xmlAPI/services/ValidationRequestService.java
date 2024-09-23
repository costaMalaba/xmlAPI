package com.cymstarlink.xmlAPI.services;

import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ValidationRequestService {
    List<ValidationRequestEntity> findAll();

    void save (ValidationRequestEntity validationRequestEntity);
}
