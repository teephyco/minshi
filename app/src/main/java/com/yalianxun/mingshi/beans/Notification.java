package com.yalianxun.mingshi.beans;

public class Notification {
    private String title;
    private String content;
    private String timestamp;
    private int countNum;
    private String imgUrl;
    private int resID;
    private int notificationType;

    public Notification(String title, String timestamp, int countNum, int resID, int notificationType) {
        this.title = title;
        this.timestamp = timestamp;
        this.countNum = countNum;
        this.resID = resID;
        this.notificationType = notificationType;
    }

    public int getResID() {
        return resID;
    }

    public void setResID(int resID) {
        this.resID = resID;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }

    public Notification(String title, String content, String timestamp, int countNum){
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.countNum = countNum;
    }

    public Notification(String title,String content,String timestamp,int countNum,String imgUrl){
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.countNum = countNum;
        this.imgUrl = imgUrl;
    }

    public int getCountNum() {
        return countNum;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
