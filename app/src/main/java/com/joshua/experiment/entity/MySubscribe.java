package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/28.
 * 订阅
 */

public class MySubscribe {
    private String id;//专辑ID
    private String url; //专辑封面
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public MySubscribe(String id, String url, String programName, String author, String introduction, String classify, String play, String model, String subscribe) {
        this.id = id;
        this.url = url;
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
        return "MySubscribe{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
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
