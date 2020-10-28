package com.firstwap.ImitraAgentSimulatorV2.Mapper;

public class mt_data {

    String username;
    String password;
    String msisdn_sender;
    String msisdn;
    String message;

    public mt_data(String username, String password, String msisdn_sender, String msisdn, String message) {
        this.username = username;
        this.password = password;
        this.msisdn_sender = msisdn_sender;
        this.msisdn = msisdn;
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

    public String getMsisdn_sender() {
        return msisdn_sender;
    }

    public void setMsisdn_sender(String msisdn_sender) {
        this.msisdn_sender = msisdn_sender;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

