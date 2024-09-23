package com.cymstarlink.xmlAPI.config;

import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import com.cymstarlink.xmlAPI.services.ResponseCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@Slf4j
@RestController
@RequestMapping("/seed")
public class Seeder {

    private final ResponseCodeService responseCodeService;
    private final HelperConfig helperConfig;

    public Seeder(ResponseCodeService responseCodeService, HelperConfig helperConfig) {
        this.responseCodeService = responseCodeService;
        this.helperConfig = helperConfig;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> seedResponseCode() {
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        HttpStatus statusCode = HttpStatus.FOUND;
        String statusMessage = "Failed";
        String[] codes = {"RGS001", "RGS002", "RGS003", "RGS004", "RGS005", "RGS006", "RGS007"};
        String[] status = {"ACCEPTED", "REJECTED", "REJECTED", "REJECTED", "FAILED", "FAILED", "FAILED"};
        String[] description = {"Success", "Message Id Already exist.", "Signature Validation Failure.", "XSD Validation Failure", "Failed to retrieve certificate", "Message Queueing Failed", "General Error or Exception"};

        if (responseCodeService.countAll() < 1) {
            for (int i = 0; i < codes.length; i++) {
                helperConfig.saveResponseCode(codes[i], status[i], description[i]);
            }
            statusCode = HttpStatus.CREATED;
            statusMessage = "Success";
        }

        response.put("statusCode", statusCode);
        response.put("Message", statusMessage);

        return new ResponseEntity<>(response, statusCode);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseCodeEntity> updateResponseCode(@PathVariable String id, @RequestBody ResponseCodeEntity request) {
        ResponseCodeEntity updatedResponseCode = responseCodeService.findByCode(id);
        updatedResponseCode.setCode(request.getCode());
        updatedResponseCode.setStatus(request.getStatus());
        updatedResponseCode.setDescription(request.getDescription());
        responseCodeService.save(updatedResponseCode);
        return ResponseEntity.ok(updatedResponseCode);
    }
}
