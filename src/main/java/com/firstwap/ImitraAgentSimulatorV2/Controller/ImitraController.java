package com.firstwap.ImitraAgentSimulatorV2.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.firstwap.ImitraAgentSimulatorV2.Mapper.MessageEntity;
import com.firstwap.ImitraAgentSimulatorV2.Mapper.Mt_data;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.mysql.cj.conf.PropertyKey.logger;
import static java.net.URLDecoder.decode;
import static org.springframework.web.util.UriUtils.encode;

@RestController
public class ImitraController {

    @Value("${acknowledge.status.message}")
    String ackstatus;

    @Value("${balance.status.message}")
    String balance;

    @Value("${count.status.message}")
    String count;

    @Autowired
    IDataRepository repository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/sendsms", method = RequestMethod.POST, consumes = {"text/xml"}, produces = {"application/xml"})
    public ResponseEntity<String> post( @RequestBody String postRequestMessage ) throws IOException
    {

        postRequestMessage = postRequestMessage.replace("data=", "");
        String decodeXMl = decode(postRequestMessage, Charset.defaultCharset());
        XmlMapper xmlMapper= new XmlMapper();
        log.info("Decode : {}", decodeXMl);

        String[] messages = decodeXMl.split("&");
        log.info("split with delimiter : {}",  Arrays.toString(messages));

        String username = (String)Array.get(messages, 0);
        String password = (String)Array.get(messages, 1);
        String msisdn = (String)Array.get(messages,2);
        String msisdnSender = (String)Array.get(messages, 3);
        String message = (String)Array.get(messages, 4);

        log.info("Param : {}", postRequestMessage);

        String agentMessageID = randomSessionID();
        MessageData messageData = new MessageData(
                agentMessageID,
                username,
                password,
                msisdn,
                msisdnSender,
                message);
        log.info("Message Data : {}", messageData);

        String response = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        if (ackstatus.equals("6801"))
        {
            response = ackstatus + " | BALANCE:" + balance + " | COUNT:" + count + " | TRANSACTIONID:" + agentMessageID;
            repository.insertDeliveryStatus(messageData);
        }
        else if(ackstatus.equals("6805"))
        {
            response = "Wrong credential (User, Password, IP)";
        }
        else if(ackstatus.equals("6806"))
        {
            response = "Unsupported prefix or unknown destination operator";
        }
        else if(ackstatus.equals("6809"))
        {
            response = "Unsupported/unregistered senderid";
        }
        else if(ackstatus.equals("6808"))
        {
            response = "Insufficient balance to be deducted";
        }
        else if(ackstatus.equals("6804"))
        {
            response = "Error from destination network operator";
        }
        else if(ackstatus.equals("6901"))
        {
            response = "Error from destination network operator – non chargable";
        }
        else if(ackstatus.equals("6902"))
        {
            response = "Fail to submit message to network operator";
        }
        else if(ackstatus.equals("6011"))
        {
            response = "Rejected message due to content filter (e.g: OTP message on regular account)";
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
