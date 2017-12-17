package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/4/28.
 * 普通用户
 */

public class PublicUser {
    private String publicID; //普通用户ID
    private String imageUrl; //图片
    private String publicName; //登录账号----唯一的
    private String introduction; //简介
    private String attentionNumber; //关注数量
    private String nickName; //普通用户名(昵称)----唯一的

    public String getPublicID() {
        return publicID;
    }

    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAttentionNumber() {
        return attentionNumber;
    }

    public void setAttentionNumber(String attentionNumber) {
        this.attentionNumber = attentionNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public PublicUser(String publicID, String imageUrl, String publicName, String introduction, String attentionNumber, String nickName) {
        this.publicID = publicID;
        this.imageUrl = imageUrl;
        this.publicName = publicName;
        this.introduction = introduction;
        this.attentionNumber = attentionNumber;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "PublicUser{" +
                "publicID='" + publicID + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publicName='" + publicName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", attentionNumber='" + attentionNumber + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
