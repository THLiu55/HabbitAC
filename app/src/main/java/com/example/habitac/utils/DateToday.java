package com.example.habitac.utils;

import java.util.Calendar;
import java.util.Date;

public class DateToday {
    Calendar cal;

    public DateToday(Date date) {
        cal = Calendar.getInstance();
        cal.setTime(date);
    }

    public static int getWeekDayOfDate(Date dt) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekDay < 0){
            weekDay = 0;
        }
        return weekDays[weekDay];
    }

    public static Date getNextDay(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
