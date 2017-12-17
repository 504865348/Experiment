package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/25.
 * 专辑购买量
 */

public class AlbumBuy {
    private String albumID;//专辑ID
    private String totalBuyTimes;//专辑购买量

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getTotalBuyTimes() {
        return totalBuyTimes;
    }

    public void setTotalBuyTimes(String totalBuyTimes) {
        this.totalBuyTimes = totalBuyTimes;
    }

    public AlbumBuy(String albumID, String totalBuyTimes) {
        this.albumID = albumID;
        this.totalBuyTimes = totalBuyTimes;
    }

    @Override
    public String toString() {
        return "AlbumBuy{" +
                "albumID='" + albumID + '\'' +
                ", totalBuyTimes='" + totalBuyTimes + '\'' +
                '}';
    }
}
