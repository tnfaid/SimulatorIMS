package com.firstwap.ImitraAgentSimulatorV2.Service;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.firstwap.ImitraAgentSimulatorV2.DbConfiguration;
import com.firstwap.ImitraAgentSimulatorV2.Mapper.dn_data;
import com.firstwap.ImitraAgentSimulatorV2.Model.MessageData;
import com.firstwap.ImitraAgentSimulatorV2.Repository.IDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Component
@EnableScheduling
@Import(value = {DbConfiguration.class})
public class UpdateDeliveryStatusThread {

    @Autowired
    IDataRepository repository;

    dn_data deliverystatus;

    @Value("${url.delivery.status}")
    String url;

    @Value("${isScheduleDeliveryOn}")
    String isOn;

    @Value("${delivery.status.message}")
    String deliveryStatus;

    Logger log = LoggerFactory.getLogger(UpdateDeliveryStatusThread.class);


    @Scheduled(cron = "1 * * * * ?")
    public void unProcess() throws JsonProcessingException {

        if (isOn.equals("1"))
        {

            List<MessageData> unprocess = repository.findDeliveryReportUnprocessed(0);
            int i = 0;
            for (MessageData messageData : unprocess)
            {

                log.info("{}", messageData);

                messageData.setDeliveryStatus(deliveryStatus);
                deliverystatus = new dn_data(messageData.getAgentMessageId(),
                        messageData.getMsisdn(),
                        messageData.getDeliveryStatus());

                String message = encodeValue(write2XMLString(deliverystatus));

                log.info("{}", message);
                HttpHeaders header = new HttpHeaders();
                header.setContentType(MediaType.APPLICATION_XML);

                RestTemplate restTemplate = new RestTemplate();

                try
                {
                    HttpEntity<String> request = new HttpEntity<>(message, header);
                    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                    System.out.print(response.getBody());

                    if (response.getBody().equals("success"))
                    {

                        repository.updateDeliveryStatus(messageData);

                    }
                    else
                    {

                        i++;

                    }

                    System.out.println("Sum of failure " + i);

                }
                catch (ResourceAccessException e)
                {

                    log.info("Connection To Generic Sending Agent is Refuse");

                }
            }
        }
        else
        {
            log.info("delivery status scheduler imitra is OFF");
        }
    }

    public static String write2XMLString(Object object) throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return xmlMapper.writeValueAsString(object);

    }

    private static String encodeValue(String value) {

        try
        {

            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());

        }
        catch (UnsupportedEncodingException ex)
        {

            throw new RuntimeException(ex.getCause());

        }
    }

}
