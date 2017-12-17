package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/15.
 * 工匠-我的问答-未处理回答
 */

public class CraftsUnDealAns {

    private String Id; //未处理问题ID
    private String askName; //提问者
    private String price; //问题价格
    private String content; //问题内容
    private String time; //提问时间


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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CraftsUnDealAns(String Id, String askName, String price, String content, String time) {
        this.Id = Id;
        this.askName = askName;
        this.price = price;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        return "CraftsUnDealAns{" +
                "askId='" + Id + '\'' +
                ", askName='" + askName + '\'' +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
