package com.yalianxun.mingshi.beans;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.ref.SoftReference;

public class PayRecord implements Parcelable {
    private String dateMonth;
    private String dateYear;
    private String location;
    private String userName;
    private String telephone;
    private String fee;//此处为总价
    private boolean status;
    /*
    **包含unpaidCharge accountBalance waterCharge electricityCharge
    * gasCharge propertyCharge parkingCharge lateFeeNotPay grossArea
    */
    private double unpaidCharge;
    private double accountBalance;
    private double waterCharge;
    private double electricityCharge;
    private double gasCharge;
    private double propertyCharge;
    private double parkingCharge;
    private double lateFeeNotPay;
    private double grossArea;
    private String payType;//缴费方式 1：现金 2：刷银行卡 3：银行卡代扣 4：预存款代扣 5:支付宝 6:微信 7:转账 8:减免用户
    private String payTime;

    public double getUnpaidCharge() {
        return unpaidCharge;
    }

    public void setUnpaidCharge(double unpaidCharge) {
        this.unpaidCharge = unpaidCharge;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double getWaterCharge() {
        return waterCharge;
    }

    public void setWaterCharge(double waterCharge) {
        this.waterCharge = waterCharge;
    }

    public double getElectricityCharge() {
        return electricityCharge;
    }

    public void setElectricityCharge(double electricityCharge) {
        this.electricityCharge = electricityCharge;
    }

    public double getGasCharge() {
        return gasCharge;
    }

    public void setGasCharge(double gasCharge) {
        this.gasCharge = gasCharge;
    }

    public double getPropertyCharge() {
        return propertyCharge;
    }

    public void setPropertyCharge(double propertyCharge) {
        this.propertyCharge = propertyCharge;
    }

    public double getParkingCharge() {
        return parkingCharge;
    }

    public void setParkingCharge(double parkingCharge) {
        this.parkingCharge = parkingCharge;
    }

    public double getLateFeeNotPay() {
        return lateFeeNotPay;
    }

    public void setLateFeeNotPay(double lateFeeNotPay) {
        this.lateFeeNotPay = lateFeeNotPay;
    }

    public double getGrossArea() {
        return grossArea;
    }

    public void setGrossArea(double grossArea) {
        this.grossArea = grossArea;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }





    public String getPayType() {
        return payType;
    }


    public String getPayTime() {
        return payTime;
    }



    public PayRecord(String dateMonth, String dateYear, String location,
                     String userName, String telephone, String fee, boolean status){
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.location = location;
        this.userName = userName;
        this.telephone = telephone;
        this.status = status;
        this.fee = fee;
    }

    public PayRecord(String dateMonth, String dateYear, String location,
                     String userName, String telephone, String fee, boolean status,
                     double[] allFee,String payType,String payTime){
        this.dateMonth = dateMonth;
        this.dateYear = dateYear;
        this.location = location;
        this.userName = userName;
        this.telephone = telephone;
        this.status = status;
        this.fee = fee;
        this.payTime = payTime;
        this.payType = payType;
//        Log.d("xph"," 111 " + payTime);
        this.unpaidCharge = allFee[0];
        this.accountBalance = allFee[1];
        this.waterCharge = allFee[2];
        this.electricityCharge = allFee[3];
        this.gasCharge = allFee[4];
        this.propertyCharge = allFee[5];
        this.parkingCharge = allFee[6];
        this.lateFeeNotPay = allFee[7];
        this.grossArea = allFee[8];
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public void setDateYear(String dateYear) {
        this.dateYear = dateYear;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDateYear() {
        return dateYear;
    }

    public String getLocation() {
        return location;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public String getFee() {
        return fee;
    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateMonth);
        dest.writeString(this.dateYear);
        dest.writeString(this.location);
        dest.writeString(this.userName);
        dest.writeString(this.telephone);
        dest.writeString(this.fee);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.unpaidCharge);
        dest.writeDouble(this.accountBalance);
        dest.writeDouble(this.waterCharge);
        dest.writeDouble(this.electricityCharge);
        dest.writeDouble(this.gasCharge);
        dest.writeDouble(this.propertyCharge);
        dest.writeDouble(this.parkingCharge);
        dest.writeDouble(this.lateFeeNotPay);
        dest.writeDouble(this.grossArea);
        dest.writeString(this.payTime);
        dest.writeString(this.payType);
    }
    private PayRecord(Parcel in){
        this.dateMonth = in.readString();
        this.dateYear = in.readString();
        this.location = in.readString();
        this.userName = in.readString();
        this.telephone = in.readString();
        this.fee = in.readString();
        this.status = in.readByte() != 0;
        this.unpaidCharge = in.readDouble();
        this.accountBalance = in.readDouble();
        this.waterCharge = in.readDouble();
        this.electricityCharge = in.readDouble();
        this.gasCharge = in.readDouble();
        this.propertyCharge = in.readDouble();
        this.parkingCharge = in.readDouble();
        this.lateFeeNotPay = in.readDouble();
        this.grossArea = in.readDouble();
        this.payTime = in.readString();
        this.payType = in.readString();

    }
    public static final Creator<PayRecord> CREATOR = new Creator<PayRecord>() {
        @Override
        public PayRecord createFromParcel(Parcel source) {
            return new PayRecord(source);
        }

        @Override
        public PayRecord[] newArray(int size) {
            return new PayRecord[size];
        }
    };
}
