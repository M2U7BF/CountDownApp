package com.example.countdownapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    ListView listView;
    Button addButton;
    ImageButton button_sort;
    private TextView textView_today;

    Common c;

    List<Map<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = (Common) getApplication();

        //ステータスバーのカスタマイズ
        getWindow().setStatusBarColor(Color.DKGRAY);

        findViews();

        //今日の日付の表示
        String text_today = "今日: " + c.today + " ";
        textView_today.setText(text_today);

        if(savedInstanceState == null) {
            setListeners();
        }
        setAdapters();
        dataReceiver();
        notificator();

    }

    protected void findViews(){
        listView = findViewById(R.id.listview);
        addButton = findViewById(R.id.button1);
        textView_today = findViewById(R.id.textView_today);
        button_sort = findViewById(R.id.imageButton_sort);
    }

    protected void setListeners(){
        addButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), ActivitySetData.class);
            startActivity(intent);
        });
        listView.setOnItemClickListener(this);
    }

    protected void setAdapters(){
        //第4引数は設定するデータリストの中のマップのキーを設定
        MySimpleAdapter adapter = new MySimpleAdapter(
                this,
                data,
                R.layout.listview_row,
                new String[] {"title", "startDate","text3","difference_of_days","text4"},
                new int[] {R.id.row_textView_title, R.id.row_textView_text,R.id.textView3, R.id.textView2, R.id.textView4});
        listView.setAdapter(adapter);
    }

    protected  void dataReceiver(){
        // 受け取りの記述
        if (c.dataset != null) {
            // ListViewに表示するリスト項目をArrayListで準備する
            for (int i = 0 ; i < c.dataset.size(); i++) {
                CompareObjects d = (CompareObjects) c.dataset.get(i);

                Map<String, String> item = new HashMap<>();
                item.put("title", d.getTitle());

                String Date;

                if( d.getDifferenceOfDate() > 0) {
                    Date = String.valueOf(d.getDifferenceOfDate());
                    item.put("text3", "");
                    item.put("text4", "日経過");
                    item.put("startDate", d.getStartDate() + " ~");
                }else if( d.getDifferenceOfDate() < 0){
                    Date = String.valueOf(-d.getDifferenceOfDate());
                    item.put("text3", "残り");
                    item.put("text4", "日");
                    item.put("startDate", "~ " + d.getStartDate());
                }else{
                    Date = "0";
                    item.put("text3", "");
                    item.put("text4", "日経過");
                    item.put("startDate", "~ " + d.getStartDate());
                }
                item.put("difference_of_days", Date);

                data.add(item);

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id){
        //clickされた要素のtext
        c.editPosition = position;

        //SubActivityに遷移
        Intent intent = new Intent(this.getApplication(), ActivityEditListElem.class);
        startActivity(intent);
    }

    public void showDialog(View view){
        DialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"my_dialog");
    }

    public void showNotificationDialog(View view){
        DialogFragment dialogFragment = new DialogFragment_Notification();
        dialogFragment.show(getSupportFragmentManager(),"notification_dialog");
    }

    public void showNotification_CustomDialog(View view){
        DialogFragment dialogFragment = new DialogFragmentForCustomNotification();
        dialogFragment.show(getSupportFragmentManager(),"notification_Custom_dialog");
    }

//    public void showTimePickerDialog(View view){
//        DialogFragment dialogFragment = new DialogFragmentForCustomNotification();
//        dialogFragment.show(getSupportFragmentManager(),"notification_Custom_dialog");
//    }

    public void push(View view){
        notificationManagerCompat.notify(1,notification);
    }

    public void notificator(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myCh","My Channel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if( data.size() > 1) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myCh")
                    .setSmallIcon(android.R.drawable.stat_notify_sync)
                    .setContentTitle(data.get(0).get("startDate"))
                    .setContentText(data.get(0).get("title") + "の通知");


            notification = builder.build();
            notificationManagerCompat = NotificationManagerCompat.from(this);
        }
    }

}
