package com.yalianxun.mingshi.beans;

public class TripRecord {
    private String timestamp;
    private String name;
    private String phone;
    private int status;

    public TripRecord(String timestamp, String name, String phone, int status) {
        this.timestamp = timestamp;
        this.name = name;
        this.phone = phone;
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
