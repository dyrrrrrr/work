package com.example.myapplication;

public class globaldata {
    private static globaldata instance;
    private String username;

    private globaldata() {}

    public static synchronized globaldata getInstance() {
        if (instance == null) {
            instance = new globaldata();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
