package com.example.myapplication;

public class moneyitem {
    private String username;
    private String aspect;
    private float num;
    private String time;
    private String bank;
    private String note;
    private moneyitem(){
        super();
        username="";
        aspect="";
        num= Float.parseFloat("");
        time="";
        bank="";
        note="";
    }
    public moneyitem(String username,String aspect,float num,String time,String bank,String note) {
        super();
        this.username=username;
        this.aspect=aspect;
        this.num=num;
        this.time=time;
        this.bank=bank;
        this.note=note;
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
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
