package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 工匠用户--我的榜单--工匠榜单
 */

public class MyBillboardCrafts {
    private String craftsRank; //工匠名次
    private String craftsImage; //工匠头像
    private String craftsName; //工匠名
    private String introduction;//简介
    private String classifyCrafts;//工匠分类
    private String hotDegree; //热度(回答问题的个数)
    //private String fansNumber; //粉丝数

    public String getCraftsRank() {
        return craftsRank;
    }

    public void setCraftsRank(String craftsRank) {
        this.craftsRank = craftsRank;
    }

    public String getCraftsImage() {
        return craftsImage;
    }

    public void setCraftsImage(String craftsImage) {
        this.craftsImage = craftsImage;
    }

    public String getCraftsName() {
        return craftsName;
    }

    public void setCraftsName(String craftsName) {
        this.craftsName = craftsName;
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

    public MyBillboardCrafts(String craftsRank, String craftsImage, String craftsName, String introduction, String classifyCrafts, String hotDegree) {
        this.craftsRank = craftsRank;
        this.craftsImage = craftsImage;
        this.craftsName = craftsName;
        this.introduction = introduction;
        this.classifyCrafts = classifyCrafts;
        this.hotDegree = hotDegree;
    }

    @Override
    public String toString() {
        return "MyBillboardCrafts{" +
                "craftsRank='" + craftsRank + '\'' +
                ", craftsImage='" + craftsImage + '\'' +
                ", craftsName='" + craftsName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", classifyCrafts='" + classifyCrafts + '\'' +
                ", hotDegree='" + hotDegree + '\'' +
                '}';
    }
}
