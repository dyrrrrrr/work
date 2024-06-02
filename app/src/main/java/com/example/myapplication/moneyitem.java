package com.example.myapplication;

public class moneyitem {
    private String username;
    private String aspect;
    private float num;
    private String time;

    private moneyitem(){
        super();
        username="";
        aspect="";
        num= Float.parseFloat("");
        time="";

    }
    public moneyitem(String username,String aspect,float num,String time) {
        super();
        this.username=username;
        this.aspect=aspect;
        this.num=num;
        this.time=time;
    }
    public String getUserame(){
        return username;
    }
    public void setUserame(String username) {
        this.username = username;
    }
    public String getAspect() {
        return aspect;
    }
    public void setAspect(String aspect) {
        this.aspect = aspect;
    }
    public float getNum() {
        return num;
    }
    public void setNum(float num) {
        this.num = num;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
