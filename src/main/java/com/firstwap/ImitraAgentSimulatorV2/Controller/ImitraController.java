package com.firstwap.ImitraAgentSimulatorV2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.firstwap.ImitraAgentSimulatorV2.Mapper.MessageEntity;
import com.firstwap.ImitraAgentSimulatorV2.Model.MessageData;
import com.firstwap.ImitraAgentSimulatorV2.Repository.IDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.Charset;

import static java.net.URLDecoder.decode;

@RestController
public class ImitraController {

    @Value("${acknowledge.status.message}")
    String ackstatus;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired IDataRepository repository;

    @RequestMapping(value = "/sendsms", method = RequestMethod.POST)
    public ResponseEntity<String> sendsms(@RequestBody String postRequestMessage ) throws IOException {

        postRequestMessage = postRequestMessage.replace("data=", "");
        String decodeXMl = decode(postRequestMessage, Charset.defaultCharset());
        ObjectMapper objectMapper = new XmlMapper();
        log.info("Decode : {}", decodeXMl);
        log.info("testooo");
        MessageEntity messageEntity = objectMapper.readValue(decodeXMl, MessageEntity.class);
        log.info("Message Model : {}", messageEntity);

        log.info("Param : {}", postRequestMessage);

        String AgentMessageID = randomSessionID();
        MessageData messageData = new MessageData(messageEntity.getUsername(),
                messageEntity.getPassword(),
                messageEntity.getMsisdn(),
                messageEntity.getMsisdnSender(),
                messageEntity.getMessage());
        log.info("Message Data : {}", messageData);

        String response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        if (ackstatus.equals("6801"))
        {

            response = ackstatus + "\n" + AgentMessageID;
            int i = repository.insertDeliveryStatus(messageData);
            log.info("Insert To DB {}", i);

        }
        else
        {

            response = ackstatus + "\n";

        }
        return new ResponseEntity<String>(response, headers, HttpStatus.ACCEPTED);

    }

    static String randomSessionID() {

        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(20);

        for (int i = 0; i < 20; i++)
        {

            int index = (int) (alphaNumericString.length() * Math.random());
            sb.append(alphaNumericString.charAt(index));

        }

        return sb.toString();
    }

}
