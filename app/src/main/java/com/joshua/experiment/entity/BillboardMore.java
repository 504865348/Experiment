package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/2.
 * 榜单-最多订阅经典榜(专辑)
 */

public class BillboardMore {
    private String albumID; //专辑ID
    private String albumRank; //专辑排名
    private String albumImage; //图片
    private String title; //标题
    private String author; //专辑所属工匠
    private String classifyName; //分类名
    private String intro; //简介
    private String model; //模块名
    private String play; //播放量
    private String subscribe; //订阅量
    private String isFocus;

    public BillboardMore(String albumID, String albumRank, String albumImage, String title, String author, String classifyName, String intro, String model, String play, String subscribe, String isFocus) {
        this.albumID = albumID;
        this.albumRank = albumRank;
        this.albumImage = albumImage;
        this.title = title;
        this.author = author;
        this.classifyName = classifyName;
        this.intro = intro;
        this.model = model;
        this.play = play;
        this.subscribe = subscribe;
        this.isFocus = isFocus;
    }

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumRank() {
        return albumRank;
    }

    public void setAlbumRank(String albumRank) {
        this.albumRank = albumRank;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
