package com.cymstarlink.xmlAPI.services;

import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import com.cymstarlink.xmlAPI.entities.ValidationResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ValidationResponseService {
    List<ValidationResponseEntity> findAll();

    void save (ValidationResponseEntity validationResponseEntity);
}
