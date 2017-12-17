package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/6/3.
 * 我的--编辑个人资料(待修改)
 */

public class EditMyInfo {
    private String headImage; //头像
    private String craftsmanName; //用户名
    private String introduction; //简介
    private String sex; //性别
    private String birthday; //生日
    private String address; //地区

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EditMyInfo(String headImage, String craftsmanName, String introduction, String sex, String birthday, String address) {
        this.headImage = headImage;
        this.craftsmanName = craftsmanName;
        this.introduction = introduction;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public String toString() {
        return "EditMyInfo{" +
                "headImage='" + headImage + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", introduction='" + introduction + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
