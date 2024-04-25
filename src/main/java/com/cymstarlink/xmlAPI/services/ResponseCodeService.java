package com.cymstarlink.xmlAPI.services;

import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseCodeService {
    List<ResponseCodeEntity> findAll();

    ResponseCodeEntity findById(int id);

    ResponseCodeEntity findByCode(String code);

    void save (ResponseCodeEntity responseCodeEntity);

    int countAll();
}
