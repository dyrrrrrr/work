package com.example.myapplication;
public class useritem {
    private String username;
    private String password;

    public useritem(){
        super();
        username="";
        password="";
    }
    public useritem(String username, String password){
        this.username=username;
        this.password=password;
    }
    public String getUserame(){
        return username;
    }
    public void setUserame(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

}
