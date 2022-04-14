package com.example.habitac.database;

import android.util.Log;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class User extends BmobObject {

    private String user_name;
    private String password;
    private String email;
    private String currentAvatarSeed;
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

    private static User foundUser;

    public static User findUser(String userName) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user_name", userName);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        Log.d("Bmob","Not found");
                    } else {
                        foundUser = list.get(0);
                    }
                }
            }
        });
        return foundUser;
    }


    public void setCurrentAvatarSeed(String seed) {
        this.currentAvatarSeed = seed;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public int addProgress(int exp) {
        // return current progress
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
