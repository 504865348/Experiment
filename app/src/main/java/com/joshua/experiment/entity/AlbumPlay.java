package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/25.
 * 专辑播放量
 */

public class AlbumPlay {
    private String albumID;//专辑ID
    private String totalPlayTimes;//专辑播放量(专辑下所有节目的总播放量)

    public String getAlbumID() {
        return albumID;
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getTotalPlayTimes() {
        return totalPlayTimes;
    }

    public void setTotalPlayTimes(String totalPlayTimes) {
        this.totalPlayTimes = totalPlayTimes;
    }

    public AlbumPlay(String albumID, String totalPlayTimes) {
        this.albumID = albumID;
        this.totalPlayTimes = totalPlayTimes;
    }

    @Override
    public String toString() {
        return "AlbumPlay{" +
                "albumID='" + albumID + '\'' +
                ", totalPlayTimes='" + totalPlayTimes + '\'' +
                '}';
    }
}
