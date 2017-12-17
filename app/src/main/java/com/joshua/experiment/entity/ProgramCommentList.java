package com.joshua.experiment.entity;

/**
 * Created by nzz on 2017/4/28.
 * 节目(视频)--评论列表（待修改）
 */

public class ProgramCommentList {
    private String commentID; //评论ID
    private String userImage; //用户头像
    private String userName; //用户名
    private String content; //内容

    public String getCommentID() {
        return commentID;
    }

    public void setCommentID(String commentID) {
        this.commentID = commentID;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ProgramCommentList(String commentID, String userImage, String userName, String content) {
        this.commentID = commentID;
        this.userImage = userImage;
        this.userName = userName;
        this.content = content;
    }

    @Override
    public String toString() {
        return "ProgramCommentList{" +
                "commentID='" + commentID + '\'' +
                ", userImage='" + userImage + '\'' +
                ", UserNameClass='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
