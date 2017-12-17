package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/6/7.
 * 工匠-我的问答-我的提问
 */

public class CraftsMyQues {
    private String quesId; //问题ID
    private String askName; //提问者
    private String price; //问题价格
    private String content; //问题内容
    private String time; //提问时间

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CraftsMyQues(String quesId, String askName, String price, String content, String time) {
        this.quesId = quesId;
        this.askName = askName;
        this.price = price;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        return "CraftsMyQues{" +
                "quesId='" + quesId + '\'' +
                ", askName='" + askName + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
