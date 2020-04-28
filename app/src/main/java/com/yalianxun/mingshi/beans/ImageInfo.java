package com.yalianxun.mingshi.beans;

public class ImageInfo {
    private String imgPath;
    private String imgName;

    public ImageInfo(String imgPath, String imgName) {
        this.imgPath = imgPath;
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
