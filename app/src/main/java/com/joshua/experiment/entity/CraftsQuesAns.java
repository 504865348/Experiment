package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 工匠用户--我的问答
 */

public class CraftsQuesAns {
    private String waitAnsID; //未处理回答ID
    private String ansID; //我的回答ID
    private String questionID; //我的提问ID
    /* 未处理回答 */
    private String questionerName; //提问者名字
    private String content; //提问内容
    private long quesTime; //提问时间
    /* 我的回答--前1个属性同“未处理回答的questionerName” */
    private String answerContent; //回答内容
    private long ansTime; //回答时间
    private String answerTime; //语音回答时长
    private int listenNumber; //收听人数
    /* 我的提问--
    前3个属性同“未处理回答”,
    后2个属性同“我的回答”后2个属性
    */

    public String getWaitAnsID() {
        return waitAnsID;
    }

    public void setWaitAnsID(String waitAnsID) {
        this.waitAnsID = waitAnsID;
    }

    public String getAnsID() {
        return ansID;
    }

    public void setAnsID(String ansID) {
        this.ansID = ansID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getQuestionerName() {
        return questionerName;
    }

    public void setQuestionerName(String questionerName) {
        this.questionerName = questionerName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getQuesTime() {
        return quesTime;
    }

    public void setQuesTime(long quesTime) {
        this.quesTime = quesTime;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public long getAnsTime() {
        return ansTime;
    }

    public void setAnsTime(long ansTime) {
        this.ansTime = ansTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public int getListenNumber() {
        return listenNumber;
    }

    public void setListenNumber(int listenNumber) {
        this.listenNumber = listenNumber;
    }

    public CraftsQuesAns(String waitAnsID, String ansID, String questionID, String questionerName, String content, long quesTime, String answerContent, long ansTime, String answerTime, int listenNumber) {
        this.waitAnsID = waitAnsID;
        this.ansID = ansID;
        this.questionID = questionID;
        this.questionerName = questionerName;
        this.content = content;
        this.quesTime = quesTime;
        this.answerContent = answerContent;
        this.ansTime = ansTime;
        this.answerTime = answerTime;
        this.listenNumber = listenNumber;
    }

    @Override
    public String toString() {
        return "CraftsQuesAns{" +
                "waitAnsID='" + waitAnsID + '\'' +
                ", ansID='" + ansID + '\'' +
                ", questionID='" + questionID + '\'' +
                ", questionerName='" + questionerName + '\'' +
                ", content='" + content + '\'' +
                ", quesTime=" + quesTime +
                ", answerContent='" + answerContent + '\'' +
                ", ansTime=" + ansTime +
                ", answerTime='" + answerTime + '\'' +
                ", listenNumber=" + listenNumber +
                '}';
    }
}
