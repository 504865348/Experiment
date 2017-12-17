package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 下载节目
 */

public class DownloadProgram {
    private String proID; //下载节目的ID
    private String image; //图片
    private String title; //标题
    private String craftsmanName; //工匠名
    private String playTimes; //播放量
    private String time; //时长

    public String getProID() {
        return proID;
    }

    public void setProID(String proID) {
        this.proID = proID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DownloadProgram(String proID, String image, String title, String craftsmanName, String playTimes, String time) {
        this.proID = proID;
        this.image = image;
        this.title = title;
        this.craftsmanName = craftsmanName;
        this.playTimes = playTimes;
        this.time = time;
    }

    @Override
    public String toString() {
        return "DownloadProgram{" +
                "proID='" + proID + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", playTimes='" + playTimes + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
