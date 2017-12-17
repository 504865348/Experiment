package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/2.
 * 榜单-付费精品飙升榜
 */

public class BillboardPay {
    private String id;//节目排名的名次
    private String recordImage;//节目的缩略图或图片
    private String recordTitle;//节目的标题
    private String name;//节目所属的作者名字
    private String downloadUrl;//节目下载的Url地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public BillboardPay(String id, String recordImage, String recordTitle, String name, String downloadUrl) {
        this.id = id;
        this.recordImage = recordImage;
        this.recordTitle = recordTitle;
        this.name = name;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "BillboardPay{" +
                "id='" + id + '\'' +
                ", recordImage='" + recordImage + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                ", name='" + name + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
