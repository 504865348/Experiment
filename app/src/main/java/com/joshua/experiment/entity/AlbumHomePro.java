package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/22.
 * 专辑主页--节目
 */

public class AlbumHomePro {
    private String id;//节目ID
    private String recordImage;//节目图片
    private String recordTitle;//节目标题
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

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public AlbumHomePro(String id, String recordImage, String recordTitle, String downloadUrl) {
        this.id = id;
        this.recordImage = recordImage;
        this.recordTitle = recordTitle;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "AlbumHomePro{" +
                "id='" + id + '\'' +
                ", recordImage='" + recordImage + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
