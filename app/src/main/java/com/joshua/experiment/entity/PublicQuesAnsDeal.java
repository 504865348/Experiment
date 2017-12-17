package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/15.
 * 普通用户--我的问答--已处理提问
 */

public class PublicQuesAnsDeal {
    private String ansId; //回答ID
    private String askName; //提问者
    private String price; //问题价格
    private String content; //问题内容
    private String timeLength; //回答时长
    private String listenrNumber; //收听人数
    private String time; //回答时间

    public String getAnsId() {
        return ansId;
    }

    public void setAnsId(String ansId) {
        this.ansId = ansId;
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

    public PublicQuesAnsDeal(String ansId, String askName, String price, String content, String timeLength, String listenrNumber, String time) {
        this.ansId = ansId;
        this.askName = askName;
        this.price = price;
        this.content = content;
        this.timeLength = timeLength;
        this.listenrNumber = listenrNumber;
        this.time = time;
    }

    @Override
    public String toString() {
        return "PublicQuesAnsDeal{" +
                "ansId='" + ansId + '\'' +
                ", askName='" + askName + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", timeLength='" + timeLength + '\'' +
                ", listenrNumber='" + listenrNumber + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
