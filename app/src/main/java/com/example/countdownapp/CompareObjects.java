package com.example.countdownapp;

import java.util.Calendar;

public class CompareObjects {
    private final String title;
    private final String startDate;
    private final int differenceOfDate;
    private String textNotificationTime = null;
    private Calendar notificationTime = null;
    private int notificationCheckedId;
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

    public int getNotificationCheckedId() {
        return notificationCheckedId;
    }

    public String getTextNotificationTime() {
        return textNotificationTime;
    }

    public void setNotificationTime(Calendar notificationTime){
        this.notificationTime = notificationTime;
    }

    public void setNotificationCheckedId(int notificationCheckedId) {
        this.notificationCheckedId = notificationCheckedId;
    }

    public void setTextNotificationTime(String textNotificationTime) {
        this.textNotificationTime = textNotificationTime;
    }
}
