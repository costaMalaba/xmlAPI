package com.cymstarlink.xmlAPI.Controllers;

import com.cymstarlink.xmlAPI.config.HelperConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequestMapping({"flutter"})

public class FlutterWaveController {
    private final HelperConfig helper;

    public FlutterWaveController(HelperConfig helper) {
        this.helper = helper;
    }

    @PostMapping(value = {"/pay/order"}, consumes = {"*/*"}, produces = {"application/json"})
    @ResponseBody
    public String payment(@RequestBody LinkedHashMap<String, Object> requestBody) {
        String userEmail = requestBody.get("email").toString();
        String amount = requestBody.get("amount").toString();
        return this.helper.sendHttpRequest(this.helper.flutterWaveCheckOut(this.helper.getRandomMessageId(), amount, userEmail), "https://api.flutterwave.com/v3/payments");
    }
}
