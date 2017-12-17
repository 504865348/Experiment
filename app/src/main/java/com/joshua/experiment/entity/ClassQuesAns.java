package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/23.
 * 问答主界面--分类--问答
 */

public class ClassQuesAns {
    private String Id;//问题ID
    private String craftsImage; //工匠头像
    private String craftsName; //工匠账号
    private String introduction; //工匠简介
    private String content; //问题内容
    private String time; //音频时长
    private String listenrNumber; //收听人数
    private String downloadUrl;//下载地址

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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

    public String getListenrNumber() {
        return listenrNumber;
    }

    public void setListenrNumber(String listenrNumber) {
        this.listenrNumber = listenrNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public ClassQuesAns(String id, String craftsImage, String craftsName, String introduction, String content, String time, String listenrNumber, String downloadUrl) {
        Id = id;
        this.craftsImage = craftsImage;
        this.craftsName = craftsName;
        this.introduction = introduction;
        this.content = content;
        this.time = time;
        this.listenrNumber = listenrNumber;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "ClassQuesAns{" +
                "Id='" + Id + '\'' +
                ", craftsImage='" + craftsImage + '\'' +
                ", craftsName='" + craftsName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", listenrNumber='" + listenrNumber + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
