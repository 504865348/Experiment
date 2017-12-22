package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/10.
 * 师生圈------暂定不用,待修改
 */

public class FriendsCircle {
    private String dynamicID; //动态ID
    private String craftsImage; //工匠头像
    private String craftsName; //工匠名
    private String context; //动态简介
    private String programImage; //节目图片
    private String title; //节目标题

    public String getDynamicID() {
        return dynamicID;
    }

    public void setDynamicID(String dynamicID) {
        this.dynamicID = dynamicID;
    }

    public String getCraftsImage() {
        return craftsImage;
    }

    public void setCraftsImage(String craftsImage) {
        this.craftsImage = craftsImage;
    }

    public String getCraftsName() {
        return craftsName;
    }

    public void setCraftsName(String craftsName) {
        this.craftsName = craftsName;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getProgramImage() {
        return programImage;
    }

    public void setProgramImage(String programImage) {
        this.programImage = programImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FriendsCircle(String dynamicID, String craftsImage, String craftsName, String context, String programImage, String title) {
        this.dynamicID = dynamicID;
        this.craftsImage = craftsImage;
        this.craftsName = craftsName;
        this.context = context;
        this.programImage = programImage;
        this.title = title;
    }

    @Override
    public String toString() {
        return "FriendsCircle{" +
                "dynamicID='" + dynamicID + '\'' +
                ", craftsImage='" + craftsImage + '\'' +
                ", craftsName='" + craftsName + '\'' +
                ", context='" + context + '\'' +
                ", programImage='" + programImage + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
