package com.example.habitac.database;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String user_name;
    private String password;
    private String email;

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

}
