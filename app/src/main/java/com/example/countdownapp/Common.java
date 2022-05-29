package com.example.countdownapp;

import android.app.Application;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Common extends Application {
    //開始日のリスト
    public List dataset = new ArrayList<>();
    private final Calendar cal_today = Calendar.getInstance();
    public SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss:SSS", Locale.JAPAN);
    public SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy/MM/dd", Locale.JAPAN);
    public String today = sdf2.format(cal_today.getTime());
    public String today_for_Activity_SetData = sdf2.format(cal_today.getTime()) + " 00:00:00:000";;
    public int elem_num = 0;
    public int editPosition = 0;
    public int clickedPosition = 0;
    public boolean sortSwitch_deadline = true;
    public boolean sortSwitch_title = false;
    public WindowManager.LayoutParams lp = null;
    public String DateReceiver = null;
    public int checkedId = 0;

    public int DateCalculator(String startDate){
        //開始日から、表示する日数の算出
        //現在の日付の取得
        Calendar cal_startDate = Calendar.getInstance();
        Calendar cal_today = Calendar.getInstance();
        long diff ;
        int difference_of_days = 0 ;

        startDate += " 00:00:00:000";

        //String→Calendar 変換
        try{
            cal_startDate.setTime(Objects.requireNonNull(sdf.parse(startDate)));
            cal_today.setTime(Objects.requireNonNull(sdf.parse(today_for_Activity_SetData)));
        }catch (ParseException e){
            cal_startDate = null;
        }

        //計算
        if( cal_startDate != null) {
            diff = cal_today.getTimeInMillis() - cal_startDate.getTimeInMillis();

            //日単位に変換
            int MILLIS_OF_DAY = 1000*60*60*24;
            difference_of_days = (int)(diff / MILLIS_OF_DAY);
        }

        return difference_of_days;
    }

    public void sortList(){
        //選択項目を取得
        //選択項目の方法でソートする
        //mainに移す
        Collections.sort(dataset, new Comparator<CompareObjects>(){
            @Override
            public int compare(CompareObjects objects, CompareObjects t1) {
                int return_i = 0;
                if (sortSwitch_deadline && !sortSwitch_title) {
                    return_i = Integer.compare(objects.getDifferenceOfDate(), t1.getDifferenceOfDate());
                } else if (!sortSwitch_deadline && sortSwitch_title) {
                    return_i = objects.getTitle().compareTo(t1.getTitle());
                }

                return return_i;
            }
        });
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }
}
