package com.joshua.experiment.entity.joshua;

import java.io.Serializable;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/8/16 21:15
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class VideoDetail implements Serializable{
    private String id;//节目排名的名次
    private String recordImage;//节目的缩略图或图片
    private String recordTitle;//节目的标题
    private String name;//节目所属的作者名字
    private String downloadUrl;//节目下载的Url地址

    private String albumID; //专辑ID
    private String idClassify; //所属分类编号----根据该编号可得所属分类信息
    private String albumImage; //图片
    private String title; //标题
    private String craftsmanName; //工匠名
    private String playTimes; //播放量
    private String subscribeTimes; //订阅量
    private String classifyName; //分类名
    private String introduction; //简介
    private String model; //所属模块(匠心独运 讲政策 听专题 看利器)
    private String isPay; //是否购买
    private String money; //是否购买
    private String isFocus;//是否收藏

    public VideoDetail(String id, String recordImage, String recordTitle, String name, String downloadUrl, String albumID, String idClassify, String albumImage, String title, String craftsmanName, String playTimes, String subscribeTimes, String classifyName, String introduction, String model, String isPay, String money, String isFocus) {
        this.id = id;
        this.recordImage = recordImage;
        this.recordTitle = recordTitle;
        this.name = name;
        this.downloadUrl = downloadUrl;
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
        this.isPay = isPay;
        this.money = money;
        this.isFocus = isFocus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordImage() {
        return recordImage;
    }

    public void setRecordImage(String recordImage) {
        this.recordImage = recordImage;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

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

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }
}
