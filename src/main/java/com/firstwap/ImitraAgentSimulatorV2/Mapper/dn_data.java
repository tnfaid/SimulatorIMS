package com.firstwap.ImitraAgentSimulatorV2.Mapper;

public class dn_data {

    String session_id;
    String msisdn;
    String error_code;

    public dn_data(String session_id, String msisdn, String error_code) {
        this.session_id = session_id;
        this.msisdn = msisdn;
        this.error_code = error_code;
    }

    public dn_data() {
    }

    @Override
    public String toString() {
        return "dn_data{" +
                "session_id='" + session_id + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", error_code='" + error_code + '\'' +
                '}';
    }
}
