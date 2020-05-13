package com.yalianxun.mingshi.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class MonthFee implements Parcelable {
    private String dateMonth;
    private String dateYear;
    private String amountTotal;
    private String amountNo;
    private String arrears;
    private String location;
    private String name;
    private String phone;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public MonthFee(String dateMonth, String dateYear, String amountTotal, String amountNo, String arrears, String location, String name, String phone) {
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.amountTotal = amountTotal;
        this.amountNo = amountNo;
        this.arrears = arrears;
        this.location = location;
        this.name = name;
        this.phone = phone;
    }


    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public String getDateYear() {
        return dateYear;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getAmountNo() {
        return amountNo;
    }

    public void setAmountNo(String amountNo) {
        this.amountNo = amountNo;
    }

    public String getArrears() {
        return arrears;
    }

    public void setArrears(String arrears) {
        this.arrears = arrears;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateMonth);
        dest.writeString(this.dateYear);
        dest.writeString(this.amountTotal);
        dest.writeString(this.amountNo);
        dest.writeString(this.arrears);
        dest.writeString(this.location);
        dest.writeString(this.name);
        dest.writeString(this.phone);
    }
    private MonthFee(Parcel in){
        this.dateMonth = in.readString();
        this.dateYear = in.readString();
        this.amountTotal = in.readString();
        this.amountNo = in.readString();
        this.arrears = in.readString();
        this.location = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
    }
    public static final Creator<MonthFee> CREATOR = new Creator<MonthFee>() {
        @Override
        public MonthFee createFromParcel(Parcel source) {
            return new MonthFee(source);
        }

        @Override
        public MonthFee[] newArray(int size) {
            return new MonthFee[size];
        }
    };
}
