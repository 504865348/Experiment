package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/5/1.
 * 提问
 */

public class Question {
    private String askId; //问题ID
    private String byAskedName; //被提问者名字
    private String askName; //提问者名字
    private String askCoins; //提问价值金额
    private String content; //提问内容
    private String imgUrl; //添加的图片
    private String contentCount; //内容字数
    private String contentLimit; //内容字数上限

    public String getByAskedName() {
        return byAskedName;
    }

    public void setByAskedName(String byAskedName) {
        this.byAskedName = byAskedName;
    }

    public String getAskCoins() {
        return askCoins;
    }

    public void setAskCoins(String askCoins) {
        this.askCoins = askCoins;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContentCount() {
        return contentCount;
    }

    public void setContentCount(String contentCount) {
        this.contentCount = contentCount;
    }

    public String getContentLimit() {
        return contentLimit;
    }

    public void setContentLimit(String contentLimit) {
        this.contentLimit = contentLimit;
    }

    public Question(String byAskedName, String askCoins, String content, String imgUrl, String contentCount, String contentLimit) {
        this.byAskedName = byAskedName;
        this.askCoins = askCoins;
        this.content = content;
        this.imgUrl = imgUrl;
        this.contentCount = contentCount;
        this.contentLimit = contentLimit;
    }

    @Override
    public String toString() {
        return "Question{" +
                "byAskedName='" + byAskedName + '\'' +
                ", askCoins='" + askCoins + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", contentCount='" + contentCount + '\'' +
                ", contentLimit='" + contentLimit + '\'' +
                '}';
    }
}
