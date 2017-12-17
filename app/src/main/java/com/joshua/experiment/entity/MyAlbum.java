package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 工匠用户--我的专辑
 */

public class MyAlbum {
    private String myAlbumID; //专辑ID
    private String albumImage; //专辑图片
    private String craftsman; //工匠名
    private String albumTitle; //专辑标题
    private String introduction; //简介
    private String programNumber; //分集数量

    public String getMyAlbumID() {
        return myAlbumID;
    }

    public void setMyAlbumID(String myAlbumID) {
        this.myAlbumID = myAlbumID;
    }

    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(String albumImage) {
        this.albumImage = albumImage;
    }

    public String getCraftsman() {
        return craftsman;
    }

    public void setCraftsman(String craftsman) {
        this.craftsman = craftsman;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(String programNumber) {
        this.programNumber = programNumber;
    }

    public MyAlbum(String myAlbumID, String albumImage, String craftsman, String albumTitle, String introduction, String programNumber) {
        this.myAlbumID = myAlbumID;
        this.albumImage = albumImage;
        this.craftsman = craftsman;
        this.albumTitle = albumTitle;
        this.introduction = introduction;
        this.programNumber = programNumber;
    }

    @Override
    public String toString() {
        return "MyAlbum{" +
                "myAlbumID='" + myAlbumID + '\'' +
                ", albumImage='" + albumImage + '\'' +
                ", craftsman='" + craftsman + '\'' +
                ", albumTitle='" + albumTitle + '\'' +
                ", introduction='" + introduction + '\'' +
                ", programNumber='" + programNumber + '\'' +
                '}';
    }
}
