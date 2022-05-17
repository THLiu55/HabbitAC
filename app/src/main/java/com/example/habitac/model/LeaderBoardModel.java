package com.example.habitac.model;

public class LeaderBoardModel {
    private String userName;
    private int userLevel;
    private int userRankingNum;

    public LeaderBoardModel(String userName, int userLevel, int userRankingNum) {
        this.userName = userName;
        this.userLevel = userLevel;
        this.userRankingNum = userRankingNum;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public int getUserRankingNum() {
        return userRankingNum;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public void setUserRankingNum(int userRankingNum) {
        this.userRankingNum = userRankingNum;
    }
}
