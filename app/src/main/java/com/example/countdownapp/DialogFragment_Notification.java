package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DialogFragment_Notification extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        Common c;
        Dialog d;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            System.out.println("mylog/" + "通知します");
            String[] choices = {"なし(デフォルト)", "当日", "前日", "2日前", "1週間前", "カスタム"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

            c = (Common)getActivity().getApplication();
            TextView showTime = dialog.findViewById(R.id.textView_notification_showTime);

            CompareObjects co;
            System.out.println("mylog/dialogが受け取っているposition : "+c.clickedPosition);
            co = (CompareObjects) c.dataset.get(c.clickedPosition);


            //再表示のための設定
            if(co.getNotificationCheckedId() != 0 && co.getNotificationCheckedId() != R.id.RadioGroup_notification1) {
                RadioButton radioButton = dialog.findViewById(co.getNotificationCheckedId());
                radioButton.setChecked(true);
            }
            if(co.getTextNotificationTime() != null){
                showTime.setText(co.getTextNotificationTime());
            }

            getChildFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
                @Override
                public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                    String result = bundle.getString("bundleKey");
                    // Do something with the result..
                    showTime.setText(result);
                }
            });


            //TimePickerの表示
            dialog.findViewById(R.id.textView_notification_showTime).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    showTimePickerDialog_notificationDialog(v);
                }
            });
            //カスタムを選択したときの処理
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
                    CompareObjects d = (CompareObjects) c.dataset.get(c.clickedPosition);
                    String startDate = d.getStartDate();
                    Calendar cal = new GregorianCalendar();
                    String calendar_text = null;


                    //状態を取得し、保存
                    //http://androidguide.nomaki.jp/html/widget/radiobutton/getchecked.html
                    final int checkedId = radioGroup.getCheckedRadioButtonId();

                    String date = null;

                    if(checkedId == R.id.RadioGroup_notification1){
                        //状態を保存
                        co.setNotificationCheckedId(checkedId);

                        //終了
                        dismiss();
                    }else {
                        switch (checkedId) {
                            case R.id.RadioGroup_notification2:
                                System.out.println("mylog/" + "当日");
                                date = startDate;
                                break;
                            case R.id.RadioGroup_notification3:
                                System.out.println("mylog/" + "前日");
                                //ミリ秒まで表示
                                try {
                                    cal.setTime(c.sdf2.parse(startDate));
                                } catch (ParseException e) {
                                    cal = null;
                                }
                                cal.add(Calendar.DATE, -1);
                                System.out.println("mylog/" + cal);
                                calendar_text = c.sdf2.format(cal.getTime());
                                System.out.println("mylog/" + calendar_text);
                                date = calendar_text;
                                break;
                            case R.id.RadioGroup_notification4:
                                System.out.println("mylog/" + "2日前");
                                //ミリ秒まで表示
                                try {
                                    cal.setTime(c.sdf2.parse(startDate));
                                } catch (ParseException e) {
                                    cal = null;
                                }
                                cal.add(Calendar.DATE, -2);
                                calendar_text = c.sdf2.format(cal.getTime());
                                date = calendar_text;
                                //時間選択へ遷移

                                break;
                            case R.id.RadioGroup_notification5:
                                System.out.println("mylog/" + "1週間前");
                                //ミリ秒まで表示
                                try {
                                    cal.setTime(c.sdf2.parse(startDate));
                                } catch (ParseException e) {
                                    cal = null;
                                }
                                cal.add(Calendar.DATE, -7);
                                calendar_text = c.sdf2.format(cal.getTime());
                                date = calendar_text;
                                break;
                        }

                        boolean saveOK = false;

                        //バリデーション
                        TextView textView = dialog.findViewById(R.id.textView_notification_showTime);
                        if("".equals(textView.getText())){
                            String toastMessage = "通知の時間が設定されていません";
                            Toast toast = Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            saveOK = true;
                        }

                        //状態を保存
                        if(saveOK) {
                            String saveTimetx = date + " " + textView.getText() + ":00:000";
                            Calendar saveTime = Calendar.getInstance();
                            try {
                                saveTime.setTime(c.sdf.parse(saveTimetx));
                            } catch (ParseException e) {
                                saveTime = null;
                            }

                            co.setTextNotificationTime((String) textView.getText());
                            co.setNotificationCheckedId(checkedId);
                            co.setNotificationTime(saveTime);

                            //終了
                            dismiss();
                        }
                    }
                }
            });
            // Close ボタンのリスナ
            dialog.findViewById(R.id.textView_notification_negative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            d = dialog;
            return dialog;
        }

        public void showTimePickerDialog_notificationDialog(View v) {
            DialogFragment newFragment = new TimePick();
            newFragment.show(getChildFragmentManager(), "timePicker");
        }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = String.format(Locale.JAPAN, "%d:%d", hourOfDay, minute);
            TextView textView = d.findViewById(R.id.textView_notification_showTime);
            textView.setText(str);
    }

}
