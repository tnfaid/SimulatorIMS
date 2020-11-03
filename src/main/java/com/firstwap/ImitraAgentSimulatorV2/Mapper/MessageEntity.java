package com.firstwap.ImitraAgentSimulatorV2.Mapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "Mt_data")
public final class MessageEntity {

    @JacksonXmlProperty(localName = "username", isAttribute = true)
    private String username;

    @JacksonXmlProperty(localName = "password", isAttribute = true)
    private String password;

    @JacksonXmlProperty(localName = "msisdn", isAttribute = true)
    private String msisdn;

    @JacksonXmlProperty(localName = "msisdn_sender", isAttribute = true)
    private String msisdnSender;

    @JacksonXmlProperty(localName = "message", isAttribute = true)
    private String message;

    public MessageEntity() {

    }

    public MessageEntity(String username, String password, String msisdn,
                         String msisdnSender, String message) {
        this.username = username;
        this.password = password;
        this.msisdn = msisdn;
        this.msisdnSender = msisdnSender;
        this.message = message;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdnSender() {
        return msisdnSender;
    }

    public void setMsisdnSender(String msisdnSender) {
        this.msisdnSender = msisdnSender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "MessageEntity{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", msisdnSender='" + msisdnSender + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
