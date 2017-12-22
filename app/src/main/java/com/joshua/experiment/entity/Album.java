package com.joshua.experiment.entity;


/**
 * Created by nzz on 2017/4/28.
 * 专辑
 */

public class Album {
    private String albumID; //专辑ID
    private String idClassify; //所属分类编号----根据该编号可得所属分类信息
    private String albumImage; //图片
    private String title; //标题
    private String craftsmanName; //工匠名
    private String playTimes; //播放量
    private String subscribeTimes; //订阅量
    private String classifyName; //分类名
    private String introduction; //简介
    private String model; //所属模块(初中 高中 大学 民间)

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getIdClassify() {
        return idClassify;
    }

    public void setIdClassify(String idClassify) {
        this.idClassify = idClassify;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public String getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(String playTimes) {
        this.playTimes = playTimes;
    }

    public String getSubscribeTimes() {
        return subscribeTimes;
    }

    public void setSubscribeTimes(String subscribeTimes) {
        this.subscribeTimes = subscribeTimes;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Album(String albumID, String idClassify, String albumImage, String title, String craftsmanName, String playTimes, String subscribeTimes, String classifyName, String introduction, String model) {
        this.albumID = albumID;
        this.idClassify = idClassify;
        this.albumImage = albumImage;
        this.title = title;
        this.craftsmanName = craftsmanName;
        this.playTimes = playTimes;
        this.subscribeTimes = subscribeTimes;
        this.classifyName = classifyName;
        this.introduction = introduction;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumID='" + albumID + '\'' +
                ", idClassify='" + idClassify + '\'' +
                ", albumImage='" + albumImage + '\'' +
                ", title='" + title + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", subscribeTimes='" + subscribeTimes + '\'' +
                ", classifyName='" + classifyName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
