package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/26.
 * 工匠的关注
 */

public class CraftAttention {
    private String userName; //用户名
    private String craftsmanName; //工匠名
    private String attentionNumber;//工匠被关注量
    private String flag;//传true则被关注量加1，传false则被关注量减1

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public String getAttentionNumber() {
        return attentionNumber;
    }

    public void setAttentionNumber(String attentionNumber) {
        this.attentionNumber = attentionNumber;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public CraftAttention(String userName, String craftsmanName, String attentionNumber, String flag) {
        this.userName = userName;
        this.craftsmanName = craftsmanName;
        this.attentionNumber = attentionNumber;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "CraftAttention{" +
                "userName='" + userName + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", attentionNumber='" + attentionNumber + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
