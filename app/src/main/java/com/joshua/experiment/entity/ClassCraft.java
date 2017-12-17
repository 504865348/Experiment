package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/23.
 * 问答主界面--分类--工匠
 */

public class ClassCraft {
    private String id; //工匠ID
    private String craftsAccount; //工匠账号
    private String imageUrl; //图片
    private String craftsmanName; //工匠名
    private String introduction;//简介
    private String classifyCrafts;//工匠分类
    private String hotDegree; //热度(回答问题的个数)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCraftsAccount() {
        return craftsAccount;
    }

    public void setCraftsAccount(String craftsAccount) {
        this.craftsAccount = craftsAccount;
    }

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

    public String getClassifyCrafts() {
        return classifyCrafts;
    }

    public void setClassifyCrafts(String classifyCrafts) {
        this.classifyCrafts = classifyCrafts;
    }

    public String getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(String hotDegree) {
        this.hotDegree = hotDegree;
    }

    public ClassCraft(String id, String craftsAccount, String imageUrl, String craftsmanName, String introduction, String classifyCrafts, String hotDegree) {
        this.id = id;
        this.craftsAccount = craftsAccount;
        this.imageUrl = imageUrl;
        this.craftsmanName = craftsmanName;
        this.introduction = introduction;
        this.classifyCrafts = classifyCrafts;
        this.hotDegree = hotDegree;
    }

    @Override
    public String toString() {
        return "ClassCraft{" +
                "id='" + id + '\'' +
                ", craftsAccount='" + craftsAccount + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", classifyCrafts='" + classifyCrafts + '\'' +
                ", hotDegree='" + hotDegree + '\'' +
                '}';
    }
}
