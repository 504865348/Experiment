package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/22.
 * 工匠主页--问答
 */

public class CraftHomeAns {
    private String Id; //回答ID
    private String askName; //提问者
    private String price; //问题价格
    private String content; //问题内容
    private String timeLength; //回答时长
    private String listenrNumber; //收听人数
    private String time; //回答时间
    private String downloadUrl; //音频URL地址

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAskName() {
        return askName;
    }

    public void setAskName(String askName) {
        this.askName = askName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public String getListenrNumber() {
        return listenrNumber;
    }

    public void setListenrNumber(String listenrNumber) {
        this.listenrNumber = listenrNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public CraftHomeAns(String id, String askName, String price, String content, String timeLength, String listenrNumber, String time, String downloadUrl) {
        Id = id;
        this.askName = askName;
        this.price = price;
        this.content = content;
        this.timeLength = timeLength;
        this.listenrNumber = listenrNumber;
        this.time = time;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "CraftHomeAns{" +
                "Id='" + Id + '\'' +
                ", askName='" + askName + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", timeLength='" + timeLength + '\'' +
                ", listenrNumber='" + listenrNumber + '\'' +
                ", time='" + time + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
