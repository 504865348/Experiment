package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 下载专辑
 */

public class DownloadAlbum {
    private String albumID; //下载专辑的ID
    private String image; //图片
    private String title; //标题
    private String craftsmanName; //工匠名
    private int programNumber; //分集数量

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
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

    public int getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(int programNumber) {
        this.programNumber = programNumber;
    }

    public DownloadAlbum(String albumID, String image, String title, String craftsmanName, int programNumber) {
        this.albumID = albumID;
        this.image = image;
        this.title = title;
        this.craftsmanName = craftsmanName;
        this.programNumber = programNumber;
    }

    @Override
    public String toString() {
        return "DownloadAlbum{" +
                "albumID='" + albumID + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", programNumber=" + programNumber +
                '}';
    }
}
