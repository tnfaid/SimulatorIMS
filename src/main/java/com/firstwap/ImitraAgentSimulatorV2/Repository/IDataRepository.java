package com.firstwap.ImitraAgentSimulatorV2.Repository;


import com.firstwap.ImitraAgentSimulatorV2.Model.MessageData;

import java.util.List;

public interface IDataRepository {

    public int insertDeliveryStatus(MessageData messageData);

    public List<MessageData> findDeliveryReportUnprocessed(int executed);

    public void updateDeliveryStatus(MessageData messageData);


}
