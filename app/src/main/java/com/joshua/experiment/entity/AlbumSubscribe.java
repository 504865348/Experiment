package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/25.
 * 专辑订阅量
 */

public class AlbumSubscribe {
    private String albumID;//专辑ID
    private String totalSubscribeTimes;//专辑订阅量
    //private String flag;//传true则订阅量加1，传false则订阅量减1

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getTotalSubscribeTimes() {
        return totalSubscribeTimes;
    }

    public void setTotalSubscribeTimes(String totalSubscribeTimes) {
        this.totalSubscribeTimes = totalSubscribeTimes;
    }

    public AlbumSubscribe(String albumID, String totalSubscribeTimes) {
        this.albumID = albumID;
        this.totalSubscribeTimes = totalSubscribeTimes;
    }

    @Override
    public String toString() {
        return "AlbumSubscribe{" +
                "albumID='" + albumID + '\'' +
                ", totalSubscribeTimes='" + totalSubscribeTimes + '\'' +
                '}';
    }
}
