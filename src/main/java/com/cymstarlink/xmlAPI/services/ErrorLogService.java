package com.cymstarlink.xmlAPI.services;

import com.cymstarlink.xmlAPI.entities.ErrorLogEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ErrorLogService {
    List<ErrorLogEntity> findAll();

    void save(ErrorLogEntity errorLogEntity);
}
