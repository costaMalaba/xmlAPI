package com.cymstarlink.xmlAPI.config;

import com.cymstarlink.xmlAPI.services.ResponseCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Seeder {

    private final ResponseCodeService responseCodeService;
    private final HelperConfig helperConfig;

    public Seeder(ResponseCodeService responseCodeService, HelperConfig helperConfig) {
        this.responseCodeService = responseCodeService;
        this.helperConfig = helperConfig;
    }

    @GetMapping("/seed")
    public String seedResponseCode() {
        String[] codes = {"RGS001", "RGS002", "RGS003", "RGS004", "RGS005", "RGS006", "RGS007"};
        String[] status = {"ACCEPTED", "REJECTED", "REJECTED", "REJECTED", "FAILED", "FAILED", "FAILED"};
        String[] description = {"Success", "Message Id Already exist.", "Signature Validation Failure.", "XSD Validation Failure", "Failed to retrieve certificate", "Message Queueing Failed", "General Error or Exception"};

        if(responseCodeService.countAll() < 1) {
            for (int i = 0; i < codes.length; i++) {
                helperConfig.saveResponseCode(codes[i], status[i], description[i]);
            }
        }

        return "Success";
    }
}
