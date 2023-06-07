package com.example.healthgo;

public class AlarmModel {
    int img;
    String msg;
    String time;
    String date;

    public AlarmModel(int img, String msg, String time, String date) {
        this.img = img;
        this.msg = msg;
        this.time = time;
        this.date = date;
    }

    public AlarmModel() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
