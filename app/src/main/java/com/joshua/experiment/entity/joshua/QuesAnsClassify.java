package com.joshua.experiment.entity.joshua;

import java.io.Serializable;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/8/14 21:06
 * <p>
 * 描 述 ：问答实体
 * <p>
 * ============================================================
 **/

public class QuesAnsClassify implements Serializable{
    private String id;//序列号
    private String userId;//提问者Id
    private String userName;//提问者名称
    private String craftsmanId;//工匠Id（回答者）
    private String craftsmanName;//工匠名称（回答者）
    private String listenNumber; //收听人数
    private String ansterTime;//回答时间
    private String vedioTimes;//音频时长
    private String money;// 匠币
    private String craftsImage; //工匠头像
    private String introduction; //工匠简介
    private String questionWord; //问题文字
    private String questionPic; //问题图片
    private String answerAmr; //音频答案 amr格式
    private String queTime;//提问时间
    private String isDeal;//是否处理
    private String isPay;//是否支付

    public QuesAnsClassify(String id, String userId, String userName, String craftsmanId, String craftsmanName, String listenNumber, String ansterTime, String vedioTimes, String money, String craftsImage, String introduction, String questionWord, String questionPic, String answerAmr, String queTime, String isDeal, String isPay) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.craftsmanId = craftsmanId;
        this.craftsmanName = craftsmanName;
        this.listenNumber = listenNumber;
        this.ansterTime = ansterTime;
        this.vedioTimes = vedioTimes;
        this.money = money;
        this.craftsImage = craftsImage;
        this.introduction = introduction;
        this.questionWord = questionWord;
        this.questionPic = questionPic;
        this.answerAmr = answerAmr;
        this.queTime = queTime;
        this.isDeal = isDeal;
        this.isPay = isPay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCraftsmanId() {
        return craftsmanId;
    }

    public void setCraftsmanId(String craftsmanId) {
        this.craftsmanId = craftsmanId;
    }

    public String getCraftsmanName() {
        return craftsmanName;
    }

    public void setCraftsmanName(String craftsmanName) {
        this.craftsmanName = craftsmanName;
    }

    public String getListenNumber() {
        return listenNumber;
    }

    public void setListenNumber(String listenNumber) {
        this.listenNumber = listenNumber;
    }

    public String getAnsterTime() {
        return ansterTime;
    }

    public void setAnsterTime(String ansterTime) {
        this.ansterTime = ansterTime;
    }

    public String getVedioTimes() {
        return vedioTimes;
    }

    public void setVedioTimes(String vedioTimes) {
        this.vedioTimes = vedioTimes;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCraftsImage() {
        return craftsImage;
    }

    public void setCraftsImage(String craftsImage) {
        this.craftsImage = craftsImage;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getQuestionWord() {
        return questionWord;
    }

    public void setQuestionWord(String questionWord) {
        this.questionWord = questionWord;
    }

    public String getQuestionPic() {
        return questionPic;
    }

    public void setQuestionPic(String questionPic) {
        this.questionPic = questionPic;
    }

    public String getAnswerAmr() {
        return answerAmr;
    }

    public void setAnswerAmr(String answerAmr) {
        this.answerAmr = answerAmr;
    }

    public String getQueTime() {
        return queTime;
    }

    public void setQueTime(String queTime) {
        this.queTime = queTime;
    }

    public String getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(String isDeal) {
        this.isDeal = isDeal;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    @Override
    public String toString() {
        return "QuesAnsClassify{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", craftsmanId='" + craftsmanId + '\'' +
                ", craftsmanName='" + craftsmanName + '\'' +
                ", listenNumber='" + listenNumber + '\'' +
                ", ansterTime='" + ansterTime + '\'' +
                ", vedioTimes='" + vedioTimes + '\'' +
                ", money='" + money + '\'' +
                ", craftsImage='" + craftsImage + '\'' +
                ", introduction='" + introduction + '\'' +
                ", questionWord='" + questionWord + '\'' +
                ", questionPic='" + questionPic + '\'' +
                ", answerAmr='" + answerAmr + '\'' +
                ", queTime='" + queTime + '\'' +
                ", isDeal='" + isDeal + '\'' +
                ", isPay='" + isPay + '\'' +
                '}';
    }
}
