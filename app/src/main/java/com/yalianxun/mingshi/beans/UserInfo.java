package com.yalianxun.mingshi.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    private String globalId;
    private String userId;
    private String name;
    private String phone;
    private String houseNum;
    private String projectId;
    private String projectName;
    private String buildingName;
    private String houseType;
    private String serviceTel;

    public UserInfo(String globalId, String userId, String name, String phone, String houseNum, String projectId, String projectName, String buildingName, String houseType) {
        this.globalId = globalId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.houseNum = houseNum;
        this.projectId = projectId;
        this.projectName = projectName;
        this.buildingName = buildingName;
        this.houseType = houseType;
    }

    public UserInfo(String globalId, String userId, String name, String phone, String houseNum, String projectId, String projectName, String buildingName, String houseType, String serviceTel) {
        this.globalId = globalId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.houseNum = houseNum;
        this.projectId = projectId;
        this.projectName = projectName;
        this.buildingName = buildingName;
        this.houseType = houseType;
        this.serviceTel = serviceTel;
    }

    public String getServiceTel() {
        return serviceTel;
    }

    public void setServiceTel(String serviceTel) {
        this.serviceTel = serviceTel;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.globalId);
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.houseNum);
        dest.writeString(this.projectId);
        dest.writeString(this.projectName);
        dest.writeString(this.buildingName);
        dest.writeString(this.houseType);
        dest.writeString(this.serviceTel);
    }
    private UserInfo(Parcel in){
        this.globalId = in.readString();
        this.userId = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.houseNum = in.readString();
        this.projectId = in.readString();
        this.projectName = in.readString();
        this.buildingName = in.readString();
        this.houseType = in.readString();
        this.serviceTel = in.readString();
    }
    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
