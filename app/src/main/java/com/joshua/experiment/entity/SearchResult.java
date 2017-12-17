package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/4/29.
 * 搜索结果
 */

public class SearchResult {
    /* 专辑 */
    private String albumImage; //专辑图片
    private String title; //标题
    private String albumIntroduction; //专辑简介
    private String playTimes; //播放量
    private String albumNumber; //搜索专辑所得数量
    /* 问答 */
    private String quesCraftsImage; //问答对应的工匠图片
    private String quesCraftsName; //问答对应的工匠名
    private String quesCraftsIntroduction; //问答对应的工匠简介
    private String content; //问题内容
    private String time; //时长
    private String quesImage; //问题图片
    private String listenrNumber; //收听人数
    private String quesAnsNumber; //搜索问答所得数量
    /* 工匠 */
    private String craftsImage; //工匠图片
    private String craftsName; //工匠名
    private String craftsIntroduction; //工匠简介
    private String craftsNumber; //搜索工匠所得数量

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

    public String getAlbumIntroduction() {
        return albumIntroduction;
    }

    public void setAlbumIntroduction(String albumIntroduction) {
        this.albumIntroduction = albumIntroduction;
    }

    public String getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(String playTimes) {
        this.playTimes = playTimes;
    }

    public String getAlbumNumber() {
        return albumNumber;
    }

    public void setAlbumNumber(String albumNumber) {
        this.albumNumber = albumNumber;
    }

    public String getQuesCraftsImage() {
        return quesCraftsImage;
    }

    public void setQuesCraftsImage(String quesCraftsImage) {
        this.quesCraftsImage = quesCraftsImage;
    }

    public String getQuesCraftsName() {
        return quesCraftsName;
    }

    public void setQuesCraftsName(String quesCraftsName) {
        this.quesCraftsName = quesCraftsName;
    }

    public String getQuesCraftsIntroduction() {
        return quesCraftsIntroduction;
    }

    public void setQuesCraftsIntroduction(String quesCraftsIntroduction) {
        this.quesCraftsIntroduction = quesCraftsIntroduction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQuesImage() {
        return quesImage;
    }

    public void setQuesImage(String quesImage) {
        this.quesImage = quesImage;
    }

    public String getListenrNumber() {
        return listenrNumber;
    }

    public void setListenrNumber(String listenrNumber) {
        this.listenrNumber = listenrNumber;
    }

    public String getQuesAnsNumber() {
        return quesAnsNumber;
    }

    public void setQuesAnsNumber(String quesAnsNumber) {
        this.quesAnsNumber = quesAnsNumber;
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

    public String getCraftsIntroduction() {
        return craftsIntroduction;
    }

    public void setCraftsIntroduction(String craftsIntroduction) {
        this.craftsIntroduction = craftsIntroduction;
    }

    public String getCraftsNumber() {
        return craftsNumber;
    }

    public void setCraftsNumber(String craftsNumber) {
        this.craftsNumber = craftsNumber;
    }

    public SearchResult(String albumImage, String title, String albumIntroduction, String playTimes, String albumNumber, String quesCraftsImage, String quesCraftsName, String quesCraftsIntroduction, String content, String time, String quesImage, String listenrNumber, String quesAnsNumber, String craftsImage, String craftsName, String craftsIntroduction, String craftsNumber) {
        this.albumImage = albumImage;
        this.title = title;
        this.albumIntroduction = albumIntroduction;
        this.playTimes = playTimes;
        this.albumNumber = albumNumber;
        this.quesCraftsImage = quesCraftsImage;
        this.quesCraftsName = quesCraftsName;
        this.quesCraftsIntroduction = quesCraftsIntroduction;
        this.content = content;
        this.time = time;
        this.quesImage = quesImage;
        this.listenrNumber = listenrNumber;
        this.quesAnsNumber = quesAnsNumber;
        this.craftsImage = craftsImage;
        this.craftsName = craftsName;
        this.craftsIntroduction = craftsIntroduction;
        this.craftsNumber = craftsNumber;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "albumImage='" + albumImage + '\'' +
                ", title='" + title + '\'' +
                ", albumIntroduction='" + albumIntroduction + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", albumNumber='" + albumNumber + '\'' +
                ", quesCraftsImage='" + quesCraftsImage + '\'' +
                ", quesCraftsName='" + quesCraftsName + '\'' +
                ", quesCraftsIntroduction='" + quesCraftsIntroduction + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", quesImage='" + quesImage + '\'' +
                ", listenrNumber='" + listenrNumber + '\'' +
                ", quesAnsNumber='" + quesAnsNumber + '\'' +
                ", craftsImage='" + craftsImage + '\'' +
                ", craftsName='" + craftsName + '\'' +
                ", craftsIntroduction='" + craftsIntroduction + '\'' +
                ", craftsNumber='" + craftsNumber + '\'' +
                '}';
    }
}
