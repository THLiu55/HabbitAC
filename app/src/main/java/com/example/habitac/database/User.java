package com.example.habitac.database;

import cn.bmob.v3.BmobObject;

public class User extends BmobObject {

    private String account;
    private String password;
    private String email;
    private String name;

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

    public void setAccount(String account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

}
