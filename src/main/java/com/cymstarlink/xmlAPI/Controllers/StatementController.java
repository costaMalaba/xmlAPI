package com.cymstarlink.xmlAPI.Controllers;

import com.cymstarlink.xmlAPI.config.HelperConfig;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping({"statement"})
@Slf4j
public class StatementController {

    private final HelperConfig helper;

    public StatementController(HelperConfig helper) {
        this.helper = helper;
    }

    @PostMapping(value = {"/req"}, consumes = {"*/*"}, produces = {"application/xml", "text/xml"})
    @ResponseBody
    public ResponseEntity<String> statement(@RequestBody String statementReq) {
        LinkedHashMap<String, Object> requestData = new LinkedHashMap<>();

        if (!this.helper.validateXmlSchema("C:\\Users\\costa\\Documents\\Projects\\xmlAPI\\src\\main\\resources\\StatementReq.xsd", statementReq)) {
            return new ResponseEntity<>(this.helper.DocumentToXml(this.helper.getDemandResponse("TARATZT", "TANZTZT", this.helper.getRandomMessageId(), this.helper.getCurrentDateTime(), this.helper.getResponseStatus("RGS003"), "RGS003", this.helper.getResponseDescription("RGS003"))), HttpStatus.OK);
        }
        try {
            Document document = DocumentHelper.parseText(statementReq);
            List<Node> headerNodes = document.selectNodes("/Rgs/Statement/Header");
            List<Node> reqSumNodes = document.selectNodes("/Rgs/Statement/RequestSummary");
            for (Node node : headerNodes) {
                requestData.put("Sender", node.selectSingleNode("Sender").getText());
                requestData.put("Receiver", node.selectSingleNode("Receiver").getText());
                requestData.put("MsgId", node.selectSingleNode("MsgId").getText());
            }

            for (Node node : reqSumNodes) {
                requestData.put("RequestId", node.selectSingleNode("RequestId").getText());
                requestData.put("CreDtTm", node.selectSingleNode("CreDtTm").getText());
                requestData.put("AcctNum", node.selectSingleNode("AcctNum").getText());
                requestData.put("SmDt", node.selectSingleNode("SmDt").getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String sender = requestData.get("Sender").toString();
        String receiver = requestData.get("Receiver").toString();
        String messageId = requestData.get("MsgId").toString();

        String requestId = requestData.get("RequestId").toString();
        String creDtTm = requestData.get("CreDtTm").toString();
        String acctNum = requestData.get("AcctNum").toString();
        String tmDt = requestData.get("SmDt").toString();
        return new ResponseEntity<>(this.helper.DocumentToXml(this.helper.getDemandResponse(sender, receiver, this.helper.getRandomMessageId(), this.helper.getCurrentDateTime(), this.helper.getResponseStatus("RGS001"), "RGS001", this.helper.getResponseDescription("RGS001"))), HttpStatus.OK);
    }
}
