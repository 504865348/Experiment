package com.joshua.experiment.entity;


/**
 * Created by nzz on 2017/5/1.
 * 普通用户--我的匠币
 */

public class PublicCoins {
    private String coinsNumber; //匠币数
    private String publicName; //普通用户名

    public String getCoinsNumber() {
        return coinsNumber;
    }

    public void setCoinsNumber(String coinsNumber) {
        this.coinsNumber = coinsNumber;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public PublicCoins(String coinsNumber, String publicName) {
        this.coinsNumber = coinsNumber;
        this.publicName = publicName;
    }

    @Override
    public String toString() {
        return "PublicCoins{" +
                "coinsNumber='" + coinsNumber + '\'' +
                ", publicName='" + publicName + '\'' +
                '}';
    }
}
