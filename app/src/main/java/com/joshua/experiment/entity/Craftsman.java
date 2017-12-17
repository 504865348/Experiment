package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/4/29.
 *首页--工匠
 */

public class Craftsman {
    private String imageUrl; //图片
    private String craftsmanName; //登录账号----唯一的
    private String introduction; //工匠简介
    private String craftsID; //工匠ID
    private String attentionNumber; //关注数量
    private String fansNumber; //粉丝数量
    private String nickName; //工匠名(昵称)----唯一的
    private String ansTimes;//回答问题次数
    private String classify; //分类

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCraftsID() {
        return craftsID;
    }

    public void setCraftsID(String craftsID) {
        this.craftsID = craftsID;
    }

    public String getAttentionNumber() {
        return attentionNumber;
    }

    public void setAttentionNumber(String attentionNumber) {
        this.attentionNumber = attentionNumber;
    }

    public String getFansNumber() {
        return fansNumber;
    }

    public void setFansNumber(String fansNumber) {
        this.fansNumber = fansNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAnsTimes() {
        return ansTimes;
    }

    public void setAnsTimes(String ansTimes) {
        this.ansTimes = ansTimes;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Craftsman(String imageUrl, String craftsmanName, String introduction, String craftsID, String attentionNumber, String fansNumber, String nickName, String ansTimes, String classify) {
        this.imageUrl = imageUrl;
        this.craftsmanName = craftsmanName;
        this.introduction = introduction;
        this.craftsID = craftsID;
        this.attentionNumber = attentionNumber;
        this.fansNumber = fansNumber;
        this.nickName = nickName;
        this.ansTimes = ansTimes;
        this.classify = classify;
    }

    @Override
    public String toString() {
        return "Craftsman{" +
                "imageUrl='" + imageUrl + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", craftsID='" + craftsID + '\'' +
                ", attentionNumber='" + attentionNumber + '\'' +
                ", fansNumber='" + fansNumber + '\'' +
                ", nickName='" + nickName + '\'' +
                ", ansTimes='" + ansTimes + '\'' +
                ", classify='" + classify + '\'' +
                '}';
    }
}
