package com.joshua.experiment.entity;

import java.util.List;

/**
 * Created by nzz on 2017/4/28.
 * 节目(视频)
 */

public class Program {
    private String programID; //节目ID
    private String idAlbum; //所属专辑编号----根据该编号可得所属专辑信息
    private String programImage; //节目缩略图
    private String title; //标题
    private String introduction; //简介
    private String playTimes; //播放量
    private String releaseTime; //发布时间
    private String programUrl; //节目链接地址
    private List commentList; //评论列表----根据评论列表中的记录数可得评论次数

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    public String getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(String idAlbum) {
        this.idAlbum = idAlbum;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getProgramUrl() {
        return programUrl;
    }

    public void setProgramUrl(String programUrl) {
        this.programUrl = programUrl;
    }

    public List getCommentList() {
        return commentList;
    }

    public void setCommentList(List commentList) {
        this.commentList = commentList;
    }

    public Program(String programID, String idAlbum, String programImage, String title, String introduction, String playTimes, String releaseTime, String programUrl, List commentList) {
        this.programID = programID;
        this.idAlbum = idAlbum;
        this.programImage = programImage;
        this.title = title;
        this.introduction = introduction;
        this.playTimes = playTimes;
        this.releaseTime = releaseTime;
        this.programUrl = programUrl;
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programID='" + programID + '\'' +
                ", idAlbum='" + idAlbum + '\'' +
                ", programImage='" + programImage + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", programUrl='" + programUrl + '\'' +
                ", commentList=" + commentList +
                '}';
    }
}
