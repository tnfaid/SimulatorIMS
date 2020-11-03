package com.firstwap.ImitraAgentSimulatorV2.Model;


import org.springframework.jdbc.core.RowMapper;

public class MessageData {

    private Long id;

    private String agentMessageId;

    private String username;

    private String password;

    private String msisdn;

    private String msisdnSender;

    private String deliveryUrl;

    private String deliveryStatus;

    private String message;

    private String executed;

    public MessageData() {
    }

    public MessageData(String agentMessageId,
                       String username,
                       String password,
                       String msisdn,
                       String msisdnSender,
                       String message) {

        this.agentMessageId = agentMessageId;
        this.username = username;
        this.password = password;
        this.msisdn = msisdn;
        this.msisdnSender = msisdnSender;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgentMessageId() {
        return agentMessageId;
    }

    public void setAgentMessageId(String agentMessageId) {
        this.agentMessageId = agentMessageId;
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

    public String getDeliveryUrl() {
        return deliveryUrl;
    }

    public void setDeliveryUrl(String deliveryUrl) {
        this.deliveryUrl = deliveryUrl;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage( String message )
    {
        this.message = message;
    }

    public static RowMapper<MessageData> getUnprocessDeliveryReport() {
        return (rs, rownum) -> {
            MessageData messageData = new MessageData();
            messageData.setAgentMessageId(rs.getString("AGENT_MESSAGE_ID"));
            messageData.setMsisdn(rs.getString("MSISDN"));
            return messageData;
        };
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "agentMessageId='" + agentMessageId + '\'' +
                ", msisdn='" + msisdn + '\'' +
                '}';
    }


}
