package com.cymstarlink.xmlAPI.services.servicesImpl;

import com.cymstarlink.xmlAPI.Repositories.ErrorLogRepository;
import com.cymstarlink.xmlAPI.entities.ErrorLogEntity;
import com.cymstarlink.xmlAPI.services.ErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {

    @Autowired
    ErrorLogRepository errorLogRepository;

    @Override
    public List<ErrorLogEntity> findAll() {
        return errorLogRepository.findAll();
    }

    @Override
    public void save(ErrorLogEntity errorLogEntity) {
        errorLogRepository.save(errorLogEntity);
    }
}
