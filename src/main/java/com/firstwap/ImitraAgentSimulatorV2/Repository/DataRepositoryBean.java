package com.firstwap.ImitraAgentSimulatorV2.Repository;

import com.firstwap.ImitraAgentSimulatorV2.Model.MessageData;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class DataRepositoryBean implements IDataRepository {

    JdbcTemplate deliveryJdbcTemplate;

    public DataRepositoryBean(DataSource deliveryDataSource) {

        this.deliveryJdbcTemplate = new JdbcTemplate(deliveryDataSource);

    }


    @Override
    public int insertDeliveryStatus(MessageData messageData) {

        final String INSERT_DR_TABLE =
                "INSERT INTO DELIVERY_STATUS " +
                        "( AGENT_MESSAGE_ID, " +
                        "USERNAME, " +
                        "PASSWORD, " +
                        "MSISDN, " +
                        "MSISDN_SENDER, " +
                        "DELIVERY_STATUS_URL, " +
                        "EXECUTED) " +
                        "VALUES " +
                        "(?, ?, ?, ?, ?, ?, ?);";

        Object[] PostDataSms = new Object[]{
                messageData.getAgentMessageId(),
                messageData.getUsername(),
                messageData.getPassword(),
                messageData.getMsisdn(),
                messageData.getMsisdnSender(),
                messageData.getDeliveryUrl(),
                messageData.getExecuted()
        };

        return deliveryJdbcTemplate.update(INSERT_DR_TABLE, PostDataSms);
    }


    @Override
    public List<MessageData> findDeliveryReportUnprocessed(int executed) {
        final String FIND_DELIVERY_STATUS_UNPROCESS =
                "SELECT AGENT_MESSAGE_ID, " +
                        "MSISDN " +
                        "FROM DELIVERY_STATUS " +
                        "WHERE EXECUTED = ? ";
        return deliveryJdbcTemplate.query(
                FIND_DELIVERY_STATUS_UNPROCESS,
                new Object[]{0},
                MessageData.getUnprocessDeliveryReport()
        );
    }


    public void updateDeliveryStatus(MessageData messageData) {

        final String UPDATE_DR_TABLE =
                "UPDATE DELIVERY_STATUS " +
                        "SET DELIVERY_STATUS = ?, " +
                        "EXECUTED = 1  " +
                        "WHERE AGENT_MESSAGE_ID = ? " +
                        "AND MSISDN = ?";
        Object[] PostDataSms = new Object[]{
                messageData.getDeliveryStatus(),
                messageData.getAgentMessageId(),
                messageData.getMsisdn()
        };

        deliveryJdbcTemplate.update(UPDATE_DR_TABLE, PostDataSms);

    }


}
