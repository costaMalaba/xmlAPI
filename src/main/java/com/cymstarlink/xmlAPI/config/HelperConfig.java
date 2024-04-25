package com.cymstarlink.xmlAPI.config;

import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import com.cymstarlink.xmlAPI.services.ResponseCodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.stereotype.Service;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelperConfig {

    private final ResponseCodeService responseCodeService;

    public HelperConfig(ResponseCodeService responseCodeService) {
        this.responseCodeService = responseCodeService;
    }

    public boolean validateXmlSchema(String xsdPath, String xmlMessage) {
        boolean isValid;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema((new File(xsdPath)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlMessage)));
            isValid = true;
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }

    public void saveResponseCode(String code, String status, String description) {
        ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
        responseCodeEntity.setId(0);
        responseCodeEntity.setCode(code);
        responseCodeEntity.setStatus(status);
        responseCodeEntity.setDescription(description);
        responseCodeService.save(responseCodeEntity);
    }

    public String getDemandResponse(String sender, String receiver, String messageId, String dateTime, String status, String code, String description) {
        String rawResponse = "<Rgs>\n" +
                "<RgsAck>\n" +
                "<Header>\n" +
                "<Sender>" + sender + "</Sender>\n" +
                "<Receiver>" + receiver + "</Receiver>\n" +
                "<MsgId>" + messageId + "</MsgId>\n" +
                "<MessageType>" + "RESPONSE" + "</MessageType>\n" +
                "</Header>\n" +
                "<ResponseSummary>\n" +
                "<CreDtTm>" + dateTime + "</CreDtTm>\n" +
                "<RespStatus>" + status + "</RespStatus>\n" +
                "<RespStatusCode>" + code + "</RespStatusCode>\n" +
                "<Description>" + description + "</Description>\n" +
                "</ResponseSummary>\n" +
                "</RgsAck>\n" +
                "<RgsSignature>" + "Signature Goes Here" + "</RgsSignature>\n" +
                "</Rgs>";
        return rawResponse;
    }

    public String DocumentToXml(String xmlstring) {
        Document xmlDocument = null;
        String xmlText = null;
        try {
            xmlDocument = DocumentHelper.parseText(xmlstring);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (xmlDocument != null) {
            xmlText = xmlDocument.asXML();
        }
        return xmlText;
    }

    public String getCurrentDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }

    public String getResponseDescription(String code) {
        ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
        responseCodeEntity = responseCodeService.findByCode(code);
        return responseCodeEntity.getDescription();
    }

    public String getResponseStatus(String code) {
        ResponseCodeEntity responseCodeEntity = new ResponseCodeEntity();
        responseCodeEntity = responseCodeService.findByCode(code);
        return responseCodeEntity.getStatus();
    }

    public String getRandomMessageId() {
        String mills = String.valueOf(System.currentTimeMillis());
        return String.format("TR%s%s", mills, RandomStringUtils.random(5, true, true)).toUpperCase();
    }
}
