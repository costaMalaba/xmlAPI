package com.cymstarlink.xmlAPI.Controllers;

import com.cymstarlink.xmlAPI.Repositories.ResponseCodeRepository;
import com.cymstarlink.xmlAPI.config.HelperConfig;
import com.cymstarlink.xmlAPI.entities.ValidationRequestEntity;
import com.cymstarlink.xmlAPI.services.ValidationRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("bill")
public class BillController {
    private static final Logger log = LoggerFactory.getLogger(BillController.class);
    private final HelperConfig helper;
    private final ResponseCodeRepository responseCodeRepository;


    public BillController(HelperConfig helper, ResponseCodeRepository responseCodeRepository) {
        this.helper = helper;
        this.responseCodeRepository = responseCodeRepository;
    }

    @PostMapping(value = {"/check"}, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public String bill(@RequestBody Map<String, Object> request) {
//        System.out.println("REQUEST : " + request);
//        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
//        LinkedHashMap<String, Object> data = new LinkedHashMap<>();
//        Long controlNumber = (Long) request.get("control_number");
        Integer count = (Integer) request.get("count");
        String message;
        int statusCode;
//        if (!request.containsKey("control_number")) {
//            statusCode = 2001;
//            message = "Operation Failed";
//            response.put("statusCode", statusCode);
//            response.put("statusMessage", message);
//            return response;
//        }

        //        log.info(raw_response);

//        this.helper.saveValidationRequest(controlNumber, request.toString());
//
//        statusCode = 2000;
//        message = "Operation Successfully";
//
//        response.put("statusCode", statusCode);
//        response.put("statusMessage", message);
//        data.put("controlNumber", controlNumber);
//        data.put("gateway", "GEPG");
//        data.put("payerName", "Juma Abdul");
//        data.put("paymentMode", "TISS");
//        data.put("paymentOption", "EXACT");
//        response.put("data", data);
        return responseCodeRepository.getUserDetails(count);
    }

}
