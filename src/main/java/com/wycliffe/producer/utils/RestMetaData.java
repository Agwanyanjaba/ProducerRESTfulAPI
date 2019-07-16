package com.wycliffe.producer.utils;

import java.util.Date;

public class RestMetaData {
    private Long queryTime;
    private Date date;
    private String message;

    public RestMetaData(Long queryTime, Date date, String message) {
        this.queryTime = queryTime;
        this.date = date;
        this.message = message;
    }

    public RestMetaData() {
    }

    @Override
    public String toString() {
        return "RestMetaData{" +
                "queryTime=" + queryTime +
                ", date=" + date +
                ", message='" + message + '\'' +
                '}';
    }
}
