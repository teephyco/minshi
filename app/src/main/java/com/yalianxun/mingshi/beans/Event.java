package com.yalianxun.mingshi.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private int pictureNum;
    private String timeStamp;
    private int status;// 0: 未处理 1：处理中 2：已完成
    private String currentStatus;// 家庭维修 公区报修 投诉建议
    private String content;
    private String startTime;
    private String endTime;

    public String getContent() {
        return content;
    }

    public int getPictureNum() {
        return pictureNum;
    }

    public int getStatus() {
        return status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setPictureNum(int pictureNum) {
        this.pictureNum = pictureNum;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Event(int pictureNum, String timeStamp, int status, String currentStatus, String content, String startTime, String endTime) {
        this.pictureNum = pictureNum;
        this.timeStamp = timeStamp;
        this.status = status;
        this.currentStatus = currentStatus;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeStamp);
        dest.writeString(this.currentStatus);
        dest.writeString(this.content);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeInt(this.pictureNum);
        dest.writeInt(this.status);
    }
    private Event(Parcel in){
        this.timeStamp = in.readString();
        this.currentStatus = in.readString();
        this.content = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.pictureNum = in.readInt();
        this.status = in.readInt();

    }
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
