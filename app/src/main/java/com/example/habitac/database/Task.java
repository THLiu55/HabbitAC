package com.example.habitac.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int frequency;
    private int remindHour;
    private int remindMin;
    private int classification;
    private int planDays;
    private int remainDays;
    private int isDone;

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getRemindHour() {
        return remindHour;
    }

    public void setRemindHour(int remindHour) {
        this.remindHour = remindHour;
    }

    public int getRemindMin() {
        return remindMin;
    }

    public void setRemindMin(int remindMin) {
        this.remindMin = remindMin;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public int getPlanDays() {
        return planDays;
    }

    public void setPlanDays(int planDays) {
        this.planDays = planDays;
    }

    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
