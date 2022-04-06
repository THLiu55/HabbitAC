package com.example.habitac.database;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String user_name;
    private String password;
    private String email;
    private int currentLevel;
    private int currentProgress;
    private int currentCoin;

    public User() {}

    public User(String un, String pa, String em) {
        user_name = un;
        password = pa;
        email = em;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int setProgress(int exp) {
        this.currentProgress += exp;
        if (currentProgress > maxExpAtLevel(currentLevel)) {
            currentProgress -= maxExpAtLevel(currentLevel);
            currentLevel ++;
        }

        return currentProgress;
    }

    private int maxExpAtLevel(int level) {
        // 升级条件
        int maxExp;
        maxExp = level * 4;

        return maxExp;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void changeCoin(int add) {
        this.currentCoin += add;
    }

    public int getCurrentCoin() {
        return currentCoin;
    }


}
