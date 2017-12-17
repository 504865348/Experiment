package com.joshua.experiment.entity;


/**
 * Created by nzz on 2017/4/28.
 * 首页--推荐
 */

public class HomeRecommend {
    private String recordImage;//节目图片
    private String recordTitle;//节目的标题
    private String name;//节目所属的作者名字
    private String downloadUrl;//节目下载的Url地址

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

    public HomeRecommend(String recordImage, String recordTitle, String name, String downloadUrl) {
        this.recordImage = recordImage;
        this.recordTitle = recordTitle;
        this.name = name;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "HomeRecommend{" +
                "recordImage='" + recordImage + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                ", name='" + name + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
