package com.joshua.experiment.entity;


/**
 * Created by nzz on 2017/6/5.
 * 首页--分类
 */

public class Classify {
    private String Id; //专辑ID
    private String albumImage; //图片
    private String title; //专辑标题
    private String craftsmanName; //工匠名
    private String introduction; //专辑简介
    private String model; //所属模块(匠心独运 讲政策 听专题 看利器)
    private String classify;//专辑所属分类
    private String play; //专辑下的节目播放量
    private String subscribe; //专辑订阅量
    private String isFocus;

    public Classify(String id, String albumImage, String title, String craftsmanName, String introduction, String model, String classify, String play, String subscribe, String isFocus) {
        Id = id;
        this.albumImage = albumImage;
        this.title = title;
        this.craftsmanName = craftsmanName;
        this.introduction = introduction;
        this.model = model;
        this.classify = classify;
        this.play = play;
        this.subscribe = subscribe;
        this.isFocus = isFocus;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }
}
