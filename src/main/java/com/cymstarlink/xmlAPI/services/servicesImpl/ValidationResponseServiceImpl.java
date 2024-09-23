package com.cymstarlink.xmlAPI.services.servicesImpl;

import com.cymstarlink.xmlAPI.Repositories.ValidationRequestRepository;
import com.cymstarlink.xmlAPI.Repositories.ValidationResponseRepository;
import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import com.cymstarlink.xmlAPI.entities.ValidationResponseEntity;
import com.cymstarlink.xmlAPI.services.ValidationRequestService;
import com.cymstarlink.xmlAPI.services.ValidationResponseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationResponseServiceImpl implements ValidationResponseService {
    private final ValidationResponseRepository validationResponseRepository;

    public ValidationResponseServiceImpl(ValidationResponseRepository validationResponseRepository) {
        this.validationResponseRepository = validationResponseRepository;
    }

    @Override
    public List<ValidationResponseEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(ValidationResponseEntity validationResponseEntity) {
        validationResponseRepository.save(validationResponseEntity);
    }
}
