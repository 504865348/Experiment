package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/2.
 * 榜单-最大国工匠榜
 */

public class BillboardCraftsman {
    private String id;//工匠排名的名次
    private String craftsAccount; //工匠账号
    private String imageUrl;//工匠头像
    private String craftsmanName;//工匠名字
    private String introduction;//工匠简介
    private String classifyCrafts;//工匠分类
    private String hotDegree; //热度(回答问题的个数)
    private String isFocus;

    public BillboardCraftsman(String id, String craftsAccount, String imageUrl, String craftsmanName, String introduction, String classifyCrafts, String hotDegree, String isFocus) {
        this.id = id;
        this.craftsAccount = craftsAccount;
        this.imageUrl = imageUrl;
        this.craftsmanName = craftsmanName;
        this.introduction = introduction;
        this.classifyCrafts = classifyCrafts;
        this.hotDegree = hotDegree;
        this.isFocus = isFocus;
    }

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

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
    }
}
