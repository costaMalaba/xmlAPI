package com.cymstarlink.xmlAPI.services.servicesImpl;

import com.cymstarlink.xmlAPI.Repositories.ResponseCodeRepository;
import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import com.cymstarlink.xmlAPI.services.ResponseCodeService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseCodeServiceImpl implements ResponseCodeService {
    private final ResponseCodeRepository responseCodeRepository;

    public ResponseCodeServiceImpl(ResponseCodeRepository responseCodeRepository) {
        this.responseCodeRepository = responseCodeRepository;
    }

    @Override
    public List<ResponseCodeEntity> findAll() {
        return responseCodeRepository.findAll();
    }

    @Override
    public ResponseCodeEntity findById(int id) {
        Optional<ResponseCodeEntity> result = responseCodeRepository.findById(id);
        ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
        if(result.isPresent()) {
            responseCodeEntity = result.get();
            return  responseCodeEntity;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseCodeEntity findByCode(String code) {
        return responseCodeRepository.findByCode(code);
    }

    @Override
    public void save(ResponseCodeEntity responseCodeEntity) {
        responseCodeRepository.save(responseCodeEntity);
    }

    @Override
    @Transactional
    public int countAll() {
        return responseCodeRepository.countAll();
    }
}
