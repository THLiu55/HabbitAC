package com.example.habitac.database;

import cn.bmob.v3.BmobObject;


public class Item extends BmobObject{
    private String type, own;
    private int num;

    private int health, attack, defense, agility;

    public Item(int num, String type, String own) {
        this.type = type;
        this.num = num;
        this.own = own;
    }

    public int getNum() {
        return num;
    }

    public String getOwn() {
        return own;
    }

    public String getType() {
        return type;
    }

    public int getAttack() {
        return attack;
    }

    public int getAgility() {
        return agility;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setOwn(String own) {
        this.own = own;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
