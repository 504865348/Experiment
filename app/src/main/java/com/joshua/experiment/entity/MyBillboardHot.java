package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/2.
 * 工匠用户--我的榜单--最火节目飙升榜(节目----按播放次数排序)
 */

public class MyBillboardHot {
    private String id;//节目ID
    private String proRank; //节目名次
    private String recordImage;//节目图片
    private String recordTitle;//节目标题
    private String downloadUrl;//节目下载的Url地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProRank() {
        return proRank;
    }

    public void setProRank(String proRank) {
        this.proRank = proRank;
    }

    public String getRecordImage() {
        return recordImage;
    }

    public void setRecordImage(String recordImage) {
        this.recordImage = recordImage;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public MyBillboardHot(String id, String proRank, String recordImage, String recordTitle, String downloadUrl) {
        this.id = id;
        this.proRank = proRank;
        this.recordImage = recordImage;
        this.recordTitle = recordTitle;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "MyBillboardHot{" +
                "id='" + id + '\'' +
                ", proRank='" + proRank + '\'' +
                ", recordImage='" + recordImage + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
