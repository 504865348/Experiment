package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/7/31.
 *读取用户个人信息
 */

public class MyInfoList {
    private String id; //个人信息ID
    private String headImage; //用户头像
    private String nickName; //昵称
    private String introduce; //简介
    private String sex; //性别
    private String birthday; //生日
    private String address; //地区

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public MyInfoList(String id, String headImage, String nickName, String introduce, String sex, String birthday, String address) {
        this.id = id;
        this.headImage = headImage;
        this.nickName = nickName;
        this.introduce = introduce;
        this.sex = sex;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public String toString() {
        return "MyInfoList{" +
                "id='" + id + '\'' +
                ", headImage='" + headImage + '\'' +
                ", nickName='" + nickName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
