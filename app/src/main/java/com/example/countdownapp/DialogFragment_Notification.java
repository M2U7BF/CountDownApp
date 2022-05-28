package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogFragment_Notification extends DialogFragment{
        Common c;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            System.out.println("mylog/" + "通知します");
            String[] choices = {"なし(デフォルト)", "当日", "前日", "2日前", "1週間前", "カスタム"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

//            //Dialogレイアウトにviewを取得
//            val inflater = requireActivity().layoutInflater;
//            val root = inflater.inflate(R.layout.dialog, null)

            Dialog dialog = new Dialog(getActivity());

            // タイトル非表示
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            // フルスクリーン
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

            dialog.setContentView(R.layout.dialog_notification);
            // 背景を透明にする
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //ラジオボタンのチェックを設定する
            RadioGroup radioGroup = dialog.findViewById(R.id.RadioGroup_notification);




            dialog.findViewById(R.id.textView_notification_custum).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.showNotification_CustomDialog(getView());
                    dismiss();
                }
            });
            // OK ボタンのリスナ
            dialog.findViewById(R.id.textView_notification_positive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("mylog/" + "選択された項目を表示します");
                    c = (Common)getActivity().getApplication();
                    CompareObjects d = (CompareObjects) c.dataset.get(c.clickedPosition);
                    String startDate = d.getStartDate();
                    Calendar cal = new GregorianCalendar();
                    String calendar_text = null;


                    //状態を取得し、保存
                    //http://androidguide.nomaki.jp/html/widget/radiobutton/getchecked.html
                    final int checkedId = radioGroup.getCheckedRadioButtonId();
                    System.out.println("mylog/" + "選択された項目を表示します/"+checkedId);
                    switch (checkedId){
                        case R.id.RadioGroup_notification1:
                            break;
                        case R.id.RadioGroup_notification2:
                            System.out.println("mylog/" + "当日");
                            c.DateReceiver = startDate;
                            //時間選択へ遷移

                            break;
                        case R.id.RadioGroup_notification3:
                            System.out.println("mylog/" + "前日");
                            //ミリ秒まで表示
                            try{
                                cal.setTime(c.sdf2.parse(startDate));
                            }catch (ParseException e){
                                cal = null;
                            }
                            cal.add(Calendar.DATE,-1);
                            System.out.println("mylog/" + cal);
                            calendar_text = c.sdf2.format(cal.getTime());
                            System.out.println("mylog/" + calendar_text);
                            c.DateReceiver = calendar_text;
                            //時間選択へ遷移

                            break;
                        case R.id.RadioGroup_notification4:
                            System.out.println("mylog/" + "2日前");
                            //ミリ秒まで表示
                            try{
                                cal.setTime(c.sdf2.parse(startDate));
                            }catch (ParseException e){
                                cal = null;
                            }
                            cal.add(Calendar.DATE,-2);
                            System.out.println("mylog/" + cal);
                            calendar_text = c.sdf2.format(cal.getTime());
                            System.out.println("mylog/" + calendar_text);
                            c.DateReceiver = calendar_text;
                            //時間選択へ遷移

                            break;
                        case R.id.RadioGroup_notification5:
                            System.out.println("mylog/" + "1週間前");
                            //ミリ秒まで表示
                            try{
                                cal.setTime(c.sdf2.parse(startDate));
                            }catch (ParseException e){
                                cal = null;
                            }
                            cal.add(Calendar.DATE,-7);
                            System.out.println("mylog/" + cal);
                            calendar_text = c.sdf2.format(cal.getTime());
                            System.out.println("mylog/" + calendar_text);
                            c.DateReceiver = calendar_text;
                            //時間選択へ遷移

                            break;
                    }
                    //時間設定が必要な場合移動

                    dismiss();
                }
            });
            // Close ボタンのリスナ
            dialog.findViewById(R.id.textView_notification_negative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

//            builder.setTitle("通知の設定")
//                    .setPositiveButton("OK", (dialogInterface, i) -> {
//                    })
//                    .setItems(choices, (dialog, which) -> {
//                        c = (Common)getActivity().getApplication();
//
//                        //あと何日 の場合
//                        //当日(startDate)、前日(startDate -1 + 時間)、2日前(startDate -2 + 時間)、1週間前(startDate -7 + 時間)、 + 時間(デフォルトは0)
//                        //~日ごと(startDate + x + 時間) //倍数判定でよいのでは
//                        //毎週~曜日 //曜日判定
//
//                        CompareObjects d = (CompareObjects) c.dataset.get(c.clickedPosition);
//                        String startDate = d.getStartDate();
//                        Calendar cal = new GregorianCalendar();
//
//                        switch (which){
//                            case 0:
//                                break;
//                            case 1:
//                                System.out.println("mylog/" + "当日");
//                                c.DateReceiver = startDate;
//                                //ミリ秒まで表示
//                                try{
//                                cal.setTime(c.sdf2.parse(startDate));
//                                }catch (ParseException e){
//                                    cal = null;
//                                }
//                                cal.add(Calendar.DATE,-1);
//                                System.out.println("mylog/" + cal);
//                                String cal_text = c.sdf.format(cal.getTime());
//                                System.out.println("mylog/" + cal_text);
//                                //時間選択へ遷移
//
//                                break;
//                            case 2:
//                                System.out.println("mylog/" + "前日");
//                                //時間選択へ遷移
//                                break;
//                            case 3:
//                                System.out.println("mylog/" + "2日前");
//                                //時間選択へ遷移
//                                break;
//                            case 4:
//                                System.out.println("mylog/" + "一週間前(7日前)");
//                                //時間選択へ遷移
//                                break;
//                            case 5:
//                                System.out.println("mylog/" + "カスタム");
//                                MainActivity activity = (MainActivity) getActivity();
//                                activity.showNotification_CustomDialog(getView());
////                                DialogFragment dialogFragment = new DialogFragmentForCustomNotification();
////                                dialogFragment.show(, "notification_Custom_dialog");
//                                break;
//                        }
//                    });
//
//            return builder.create();
            return dialog;
        }

}
