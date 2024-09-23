package com.cymstarlink.xmlAPI.Controllers;

import com.cymstarlink.xmlAPI.config.HelperConfig;
import lombok.experimental.Helper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    private final HelperConfig helper;

    public EmailController(HelperConfig helper) {
        this.helper = helper;
    }

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<Object> sendMail(@RequestBody String request) {
        String response = this.helper.sendHtmlMail("costantineyohana1999@gmail.com", "HTML EMAIL TEST", "email-template");
        return ResponseEntity.ok(response);
    }
}
