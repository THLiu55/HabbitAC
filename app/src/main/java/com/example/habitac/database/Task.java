package com.example.habitac.database;

import com.example.habitac.utils.Classification;

import java.sql.Time;
import java.util.Date;



public class Task {
    String taskName;
    Date startDate;
    boolean[] frequency = new boolean[7];
    Time remindTime;
    String description;
    Classification classification;

    public boolean[] getFrequency() {
        return frequency;
    }

    public Classification getClassification() {
        return classification;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getDescription() {
        return description;
    }

    public String getTaskName() {
        return taskName;
    }

    public Time getRemindTime() {
        return remindTime;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(boolean[] frequency) {
        this.frequency = frequency;
    }

    public void setRemindTime(Time remindTime) {
        this.remindTime = remindTime;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
