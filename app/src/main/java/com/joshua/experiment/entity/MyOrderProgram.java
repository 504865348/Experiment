package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 我的订单--节目
 */

public class MyOrderProgram {
    private String programOrderID; //节目订单ID
    private String craftsmanName; //工匠名
    private String programImage; //节目图片
    private String programTitle; //节目标题
    private String playTimes; //播放量
    private String releaseTime; //发布时间
    private String programPrice; //节目价格

    public String getProgramOrderID() {
        return programOrderID;
    }

    public void setProgramOrderID(String programOrderID) {
        this.programOrderID = programOrderID;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public String getProgramImage() {
        return programImage;
    }

    public void setProgramImage(String programImage) {
        this.programImage = programImage;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

    public String getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(String playTimes) {
        this.playTimes = playTimes;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getProgramPrice() {
        return programPrice;
    }

    public void setProgramPrice(String programPrice) {
        this.programPrice = programPrice;
    }

    public MyOrderProgram(String programOrderID, String craftsmanName, String programImage, String programTitle, String playTimes, String releaseTime, String programPrice) {
        this.programOrderID = programOrderID;
        this.craftsmanName = craftsmanName;
        this.programImage = programImage;
        this.programTitle = programTitle;
        this.playTimes = playTimes;
        this.releaseTime = releaseTime;
        this.programPrice = programPrice;
    }

    @Override
    public String toString() {
        return "MyOrderProgram{" +
                "programOrderID='" + programOrderID + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", programImage='" + programImage + '\'' +
                ", programTitle='" + programTitle + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", programPrice='" + programPrice + '\'' +
                '}';
    }
}
