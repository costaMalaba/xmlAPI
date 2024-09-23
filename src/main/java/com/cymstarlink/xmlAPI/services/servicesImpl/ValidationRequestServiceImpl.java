package com.cymstarlink.xmlAPI.services.servicesImpl;

import com.cymstarlink.xmlAPI.Repositories.ValidationRequestRepository;
import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import com.cymstarlink.xmlAPI.services.ValidationRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidationRequestServiceImpl implements ValidationRequestService {
    private final ValidationRequestRepository validationRequestRepository;

    public ValidationRequestServiceImpl(ValidationRequestRepository validationRequestRepository) {
        this.validationRequestRepository = validationRequestRepository;
    }

    @Override
    public List<ValidationRequestEntity> findAll() {
        return List.of();
    }

    @Override
    public void save(ValidationRequestEntity validationRequestEntity) {
        validationRequestRepository.save(validationRequestEntity);
    }
}
