package com.example.habitac.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitac.database.User;

import java.util.Date;

public class MainViewModel extends ViewModel {
    private User user;
    private MutableLiveData<String> userName;
    private MutableLiveData<Integer> level;
    private MutableLiveData<Integer> exp;
    private MutableLiveData<Integer> coin;
    private MutableLiveData<Integer> taskTodoAmount;
    private MutableLiveData<Integer> taskAmount;

    public void setUser(User user) {
        this.user = user;
    }

    public MutableLiveData<Date> getToday() {
        return new MutableLiveData<>(new Date());
    }

    public MutableLiveData<Integer> getCoin() {
        if (coin == null) {
            coin = new MutableLiveData<>(user.getCurrentCoin());
        }
        return coin;
    }

    public void addCoin(int add) {
        if (coin == null) {
            getCoin();
        }
        coin.setValue(coin.getValue() + add);
    }

    public void minusCoin(int minus) {
        if (coin == null) {
            getCoin();
        }
        coin.setValue(coin.getValue() - minus);
    }

    public MutableLiveData<Integer> getExp() {
        if (exp == null) {
            exp = new MutableLiveData<>(user.getCurrentExp());
        }
        return exp;
    }

    public void setExp(int exp_offsite) {
        if (this.exp == null) {
            getExp();
        }
        if (user.setProgress(exp_offsite)) {
            this.level.setValue(user.getCurrentLevel());
        }
        this.exp.setValue(user.getCurrentExp());
    }

    public void setCoin(int coin_offsite) {
        if (this.coin == null) {
            getCoin();
        }
        user.setCoin(coin_offsite);
        this.coin.setValue(user.getCurrentCoin());
    }

    public MutableLiveData<Integer> getLevel() {
        if (level == null) {
            level = new MutableLiveData<>(user.getCurrentLevel());
        }
        return level;
    }

    public MutableLiveData<String> getUserName() {
        if (userName == null) {
            userName = new MutableLiveData<>(user.getUser_name());
        }
        return userName;
    }

    public void setTodoTaskAmount(int taskAmount) {
        this.taskTodoAmount = new MutableLiveData<>(taskAmount);
    }

    public MutableLiveData<Integer> getTodoTaskAmount() {
        return taskTodoAmount;
    }

    public MutableLiveData<Integer> getTaskAmount() {
        return taskAmount;
    }

    public void setTaskAmount(int taskAmount) {
        this.taskAmount = new MutableLiveData<>(taskAmount);
    }


}
