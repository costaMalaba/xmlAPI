package com.cymstarlink.xmlAPI.config;

import com.cymstarlink.xmlAPI.entities.ResponseCodeEntity;
import com.cymstarlink.xmlAPI.services.ResponseCodeService;
import org.apache.commons.codec.binary.Base64;
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
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelperConfig {

    private final ResponseCodeService responseCodeService;
    private static final String SIGNING_ALGORITHM = "SHA1withRSA";
    private static final String ALGORITHM = "RSA";

    private static final String PRIVATE_KEY_STRING = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1YUm4CxIGaMLc\n" +
            "Nk5coYuQzwKXQMrbRiMKGiS0IWrYGzsVtJBbhswAWkFRFAqLX+Sf6mLeahiAtKc3\n" +
            "adAvs0rdBifyIEZ4/y9mCAbJmckErmCErbcMOeaPI1mMOtgUaCmomxU+vDhdukA6\n" +
            "+YH5IWdDqT+DZ2S+oLO9Ly3CKHRfuYNo7Zs8lzrolDla6dWZBwknHjPI/x4lXKqF\n" +
            "GU4/SPIZlM6kUdthuSNMd4S5kUWfH+OWMPePItZSbRloJgGLMshSdekWDnypAFVh\n" +
            "YnJq+Gfif2sH01/FRdMMOHrmqBulUj2oVtFWd0tXY0dI6vsloY+F088390BZxrPy\n" +
            "onrVtWBVAgMBAAECggEAE8FO6C68fdo3LwjzK/+kVddWLuctsQhIlLcYPE5Pds4w\n" +
            "d0K9XlqmKXurFYubQmblXc2HizX/CTWj6NMri6Z5hu0+uI5vXopcvT4ujxKi5FGh\n" +
            "zZGmGDzOMdTl0fIuyro1n1mUzMpvUTYR/NpkH6BVwPbUJhnCzUhvizFySG1jj3FU\n" +
            "GUsAg0Ft/eNCnd9wU5zSehYaA/HXbUAxcPfdDnFHFyx4HUcBWdWZkpCf43neik5B\n" +
            "0bnxhG8oP2cPjSV8tkY+yOVGbkojm/dLjLlrD/JuqE77ShEIkd2puTLS6JzdTdiK\n" +
            "nuZluzaYhGqHW03taz8mTW8cWZmzwIqTEPjTi4TmAQKBgQDphn3IIF5gKcFwJd0N\n" +
            "sRd4qFn5UrxaVsmdDQn4HE/T0C1cLsmdf6iTrh9vMwzZMsy0gYnbb5HhVkJoa++F\n" +
            "370wOgq1hPYiz+3f85OrCLBcXE6llUdUAoDcOn7lZJG9p6ZMnzYi8wpuzIOdZ0RH\n" +
            "7YwvKimM4Ond4O+rIvsHGm7ZwQKBgQDG1g9bPgInBH+0uVDYJpXmdyR8F5V9nfS4\n" +
            "7ks6p0z8aiDR3hKVgWl0SLFewcYgYmXUM27Uc+UkYK7/KYIXHVSPlylOjWqgo/Xb\n" +
            "NhyKXZDBnfIYQbAspu4mtAJ05ErNymQf0qYlVCKhwqQjCY7cNLEdoR6cZ+gb4dbV\n" +
            "U37VxbtjlQKBgEzcCRYPIUDBPXtt2t2gxbreng5jXr7VBbJ9EiTpHJT4VZWbiJwf\n" +
            "4hoJGyt8P+RUxe87G2ZvyDHENEMuEz2asCfpUpD/3AsFKKOnBZkrWPBtKS1yfnF2\n" +
            "CXdygUV63kTvotphJG5IU8y33zG1RVHvK05ysA5AEHkIiZln//sMhiOBAoGBAL84\n" +
            "kC4slT9W2UZBzlkByrmIrULWH4NeTEMpkJnI2YYKvqN2kaLMgVE+s62wZ00vtER+\n" +
            "bkDNcHoqrUFhduWp5G/vbN9Jq1jwNfpqsMn0r8k0j+X6Dmby5ye7iH1ILBR7Zw0f\n" +
            "tX9CyceNe/LiN0zZM9iN4fWDDDr8V3JzSRBniA59AoGAcV0AlfjVYmUBDWE1o/A1\n" +
            "AM4EQFQScPUtLeOMYzYu0kRmbcgvPdtBDEIJegtTM9fwppKuMJ0ET+V+Gsrz2T9j\n" +
            "m0lMw+42MMOlynUDP47+DLH78F4In6yaExvlkRsUUgV605W4RJuztwuXG3kCxLhZ\n" +
            "dd1M1zpdMQCktBb3gThGLko=";

    private static final String PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtWFJuAsSBmjC3DZOXKGL\n" +
            "kM8Cl0DK20YjChoktCFq2Bs7FbSQW4bMAFpBURQKi1/kn+pi3moYgLSnN2nQL7NK\n" +
            "3QYn8iBGeP8vZggGyZnJBK5ghK23DDnmjyNZjDrYFGgpqJsVPrw4XbpAOvmB+SFn\n" +
            "Q6k/g2dkvqCzvS8twih0X7mDaO2bPJc66JQ5WunVmQcJJx4zyP8eJVyqhRlOP0jy\n" +
            "GZTOpFHbYbkjTHeEuZFFnx/jljD3jyLWUm0ZaCYBizLIUnXpFg58qQBVYWJyavhn\n" +
            "4n9rB9NfxUXTDDh65qgbpVI9qFbRVndLV2NHSOr7JaGPhdPPN/dAWcaz8qJ61bVg\n" +
            "VQIDAQAB";

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

    public String getResponseMessage(String sender, String receiver, String messageId, String dateTime, String status, String code, String description) {
        String rawResponse = "<RgsAck>\n" +
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
                "</RgsAck>";
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

    public String signRequestAndGetFullResponse(String messageToSign) {
        String incomingMessage = messageToSign;
        incomingMessage = incomingMessage.replaceAll("\\r\\n|\\r|\\n", "");
        return "<Rgs>" + messageToSign + "<RgsSignature>" + getDigitalSignature(incomingMessage) + "</RgsSignature></Rgs>";
    }

    private String getDigitalSignature(String messageToSign) {
        String signature = "";
        try {
            signature = signMessage(messageToSign);
        } catch (Exception e) {
            signature = "EXCEPTION - " + e.getMessage();
            e.printStackTrace();
        }

        return signature;
    }

    private String signMessage(String messageToSign) throws Exception {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(getPrivateKeyFromString());
        signature.update(messageToSign.trim().getBytes(StandardCharsets.UTF_8));
        byte[] messageSignature =signature.sign();
        return java.util.Base64.getEncoder().encodeToString(messageSignature);
    }

    private static RSAPrivateKey getPrivateKeyFromString() throws IOException, GeneralSecurityException {
        byte[] encoded = Base64.decodeBase64(PRIVATE_KEY_STRING);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    public boolean verifySignature(String messageToVerify, String IncomingSignature) {
        boolean isSignatureValid = false;
        try{
            isSignatureValid = verifyDigitalSignature(messageToVerify, IncomingSignature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSignatureValid;
    }

    private boolean verifyDigitalSignature(String messageToVerify, String IncomingSignature) throws Exception {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(getPublicKeyFromString());
        signature.update(messageToVerify.getBytes());
        return signature.verify(Base64.decodeBase64(IncomingSignature.getBytes()));
    }

    private static RSAPublicKey getPublicKeyFromString() throws  IOException, GeneralSecurityException {
        byte[] encode = Base64.decodeBase64(PUBLIC_KEY_STRING);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encode));
    }
}
