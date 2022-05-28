package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

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

            builder.setTitle("通知の設定")
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                    })
                    .setItems(choices, (dialog, which) -> {
                        c = (Common)getActivity().getApplication();

                        //あと何日 の場合
                        //当日(startDate)、前日(startDate -1 + 時間)、2日前(startDate -2 + 時間)、1週間前(startDate -7 + 時間)、 + 時間(デフォルトは0)
                        //~日ごと(startDate + x + 時間) //倍数判定でよいのでは
                        //毎週~曜日 //曜日判定

                        CompareObjects d = (CompareObjects) c.dataset.get(c.clickedPosition);
                        String startDate = d.getStartDate();
                        Calendar cal = new GregorianCalendar();

                        switch (which){
                            case 0:
                                break;
                            case 1:
                                System.out.println("mylog/" + "当日");
                                c.DateReceiver = startDate;
                                //ミリ秒まで表示
                                try{
                                cal.setTime(c.sdf2.parse(startDate));
                                }catch (ParseException e){
                                    cal = null;
                                }
                                cal.add(Calendar.DATE,-1);
                                System.out.println("mylog/" + cal);
                                String cal_text = c.sdf.format(cal.getTime());
                                System.out.println("mylog/" + cal_text);
                                //時間選択へ遷移

                                break;
                            case 2:
                                System.out.println("mylog/" + "前日");
                                //時間選択へ遷移
                                break;
                            case 3:
                                System.out.println("mylog/" + "2日前");
                                //時間選択へ遷移
                                break;
                            case 4:
                                System.out.println("mylog/" + "一週間前(7日前)");
                                //時間選択へ遷移
                                break;
                            case 5:
                                System.out.println("mylog/" + "カスタム");
                                MainActivity activity = (MainActivity) getActivity();
                                activity.showNotification_CustomDialog(getView());
//                                DialogFragment dialogFragment = new DialogFragmentForCustomNotification();
//                                dialogFragment.show(, "notification_Custom_dialog");
                                break;
                        }
                    });

            return builder.create();
        }

}
