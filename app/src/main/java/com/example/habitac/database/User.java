package com.example.habitac.database;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String user_name;
    private String password;
    private String email;
    private String currentAvatarSeed;
    private int currentLevel;
    private int currentExp;
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

    public void setCurrentAvatarSeed(String seed) {
        this.currentAvatarSeed = seed;
    }

    public int getCurrentExp() {
        return currentExp;
    }

    public boolean setProgress(int exp) {
        // return current progress
        this.currentExp += exp;
        if (currentExp > maxExpAtLevel()) {
            currentExp -= maxExpAtLevel();
            currentLevel++;
            return true;
        } else if (currentExp < 0) {
            if (currentLevel != 1) {
                currentLevel--;
                currentExp += maxExpAtLevel();
                return true;
            } else {
                currentExp = 0;
            }
        }
        return false;
    }

    private int maxExpAtLevel() {
        // 升级条件
        return currentLevel * 4;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCoin(int coin) {
        this.currentCoin += coin;
        if (currentCoin > maxCoinAtLevel()) {
            currentCoin = maxCoinAtLevel();
        } else if (currentCoin < 0) {
            currentCoin = 0;
        }
    }

    private int maxCoinAtLevel() {
        return currentLevel * 10;
    }

    public int getCurrentCoin() {
        return currentCoin;
    }
}
