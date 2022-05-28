package com.example.countdownapp;

import java.util.Calendar;

public class CompareObjects {
    private final String title;
    private final String startDate;
    private final int differenceOfDate;
    private final Calendar notificationTime = null;
    Common c;

    public CompareObjects(String title, String startDate){
        c = new Common();

        this.title = title;
        this.startDate = startDate;
        this.differenceOfDate = c.DateCalculator(startDate);
    }

    public String getTitle(){
        return this.title;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getDifferenceOfDate(){
        return this.differenceOfDate;
    }

}
