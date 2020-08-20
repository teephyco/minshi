package com.yalianxun.mingshi.beans;

public class FeeBean {
    private String feeTypeName;
    private String totalFee;
    private String usageAmount;
    private String unitPrice;
    private String status;

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(String usageAmount) {
        this.usageAmount = usageAmount;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
