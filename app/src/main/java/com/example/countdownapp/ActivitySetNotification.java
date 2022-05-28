package com.example.countdownapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ActivitySetNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification);

        //設定ダイアログ
        //本当はmain activity からアイコンタップで設定したい

        //----------------------------------------

        // カスタム + 日時

        //設定時間の設定
        //当日(startDate)、前日(startDate -1 + 時間)、2日前(startDate -2 + 時間)、1週間前(startDate -7 + 時間)、 + 時間(デフォルトは0)
        //~日ごと(startDate + x + 時間) //倍数判定でよいのでは
        //毎週~曜日 //曜日判定

        //if currentTime == 設定時間

        //間隔指定
    }
}