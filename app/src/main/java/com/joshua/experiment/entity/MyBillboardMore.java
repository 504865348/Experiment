package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/2.
 * 工匠用户--我的榜单--最多订阅经典版(专辑----按订阅次数排序)
 */

public class MyBillboardMore {
    private String id;//专辑ID
    private String albumRank; //专辑排名
    private String imageUrl; //专辑封面
    private String programName; //专辑名
    private String author; //专辑所属工匠
    private String introduction; //专辑简介
    private String classify; //专辑所属分类
    private String play; //专辑下的节目播放量
    private String model; //专辑所属模块
    private String subscribe; //专辑订阅量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbumRank() {
        return albumRank;
    }

    public void setAlbumRank(String albumRank) {
        this.albumRank = albumRank;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public MyBillboardMore(String id, String albumRank, String imageUrl, String programName, String author, String introduction, String classify, String play, String model, String subscribe) {
        this.id = id;
        this.albumRank = albumRank;
        this.imageUrl = imageUrl;
        this.programName = programName;
        this.author = author;
        this.introduction = introduction;
        this.classify = classify;
        this.play = play;
        this.model = model;
        this.subscribe = subscribe;
    }

    @Override
    public String toString() {
        return "MyBillboardMore{" +
                "id='" + id + '\'' +
                ", albumRank='" + albumRank + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", programName='" + programName + '\'' +
                ", author='" + author + '\'' +
                ", introduction='" + introduction + '\'' +
                ", classify='" + classify + '\'' +
                ", play='" + play + '\'' +
                ", model='" + model + '\'' +
                ", subscribe='" + subscribe + '\'' +
                '}';
    }
}
