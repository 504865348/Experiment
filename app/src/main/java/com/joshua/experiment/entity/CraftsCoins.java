package com.joshua.experiment.entity;


/**
 * Created by nzz on 2017/5/1.
 * 工匠用户--我的匠币
 */

public class CraftsCoins {
    private String coinsNumber; //匠币数
    private String craftsmanName; //工匠名

    public String getCoinsNumber() {
        return coinsNumber;
    }

    public void setCoinsNumber(String coinsNumber) {
        this.coinsNumber = coinsNumber;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public CraftsCoins(String coinsNumber, String craftsmanName) {
        this.coinsNumber = coinsNumber;
        this.craftsmanName = craftsmanName;
    }

    @Override
    public String toString() {
        return "CraftsCoins{" +
                "coinsNumber='" + coinsNumber + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                '}';
    }
}
