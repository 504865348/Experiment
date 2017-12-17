package com.joshua.experiment.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by nzz on 2017/5/1.
 * 工匠用户--我的录制
 */

public class MyRecording implements Serializable{
    private String myRecordingID; //我的录制ID
    private String recordingImage; //录制视频的图片
    private String name; //录制人名字
    private String time; //录制时间(xx年xx月xxo日 xx时xx分)
    private String duration; //视频/音频时长
    private String storageUrl; //存储地址

    public String getMyRecordingID() {
        return myRecordingID;
    }

    public void setMyRecordingID(String myRecordingID) {
        this.myRecordingID = myRecordingID;
    }

    public String getRecordingImage() {
        return recordingImage;
    }

    public void setRecordingImage(String recordingImage) {
        this.recordingImage = recordingImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public void setStorageUrl(String storageUrl) {
        this.storageUrl = storageUrl;
    }

    /*public MyRecording(String myRecordingID, String recordingImage, String name, String time, String duration, String storageUrl) {
        this.myRecordingID = myRecordingID;
        this.recordingImage = recordingImage;
        this.name = name;
        this.time = time;
        this.duration = duration;
        this.storageUrl = storageUrl;
    }*/

    public MyRecording() {
    }

    @Override
    public String toString() {
        return "MyRecording{" +
                "myRecordingID='" + myRecordingID + '\'' +
                ", recordingImage='" + recordingImage + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", storageUrl='" + storageUrl + '\'' +
                '}';
    }
}
