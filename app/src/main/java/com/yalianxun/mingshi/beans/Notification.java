package com.yalianxun.mingshi.beans;

public class Notification {
    private String title;
    private String content;
    private String timestamp;
    private int countNum;
    private String imgUrl;

    public Notification(String title,String content,String timestamp,int countNum){
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
