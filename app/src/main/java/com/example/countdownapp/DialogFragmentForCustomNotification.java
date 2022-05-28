package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentForCustomNotification extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //https://dev.classmethod.jp/articles/android-tips-45-custom-dialog/
        String[] choices = {"日前/後", "週間前/後"};
        String[] repeat = {"繰り返す"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        final EditText editTextNumber = new EditText(getActivity());
//
//        final Switch repeatSwitch = new Switch(getActivity());
//        repeatSwitch.setText("繰り返し");
//        repeatSwitch.setGravity(Gravity.CENTER);
//
//        final TextView showTime = new TextView(getActivity());

        Dialog dialog = new Dialog(getActivity());

        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.setContentView(R.layout.dialog_notification_custum);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // OK ボタンのリスナ
        dialog.findViewById(R.id.textView_positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // Close ボタンのリスナ
        dialog.findViewById(R.id.textView_negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.showNotificationDialog(getView());
                dismiss();
            }
        });


//        builder.setTitle("カスタム通知")
//                .setSingleChoiceItems(choices,0,(dialogInterface, which) -> {
//                    //選択時の挙動
//                    switch (which) {
//                        case 0:
//                            System.out.println("mylog/" + choices[0]);
//                            break;
//                        case 1:
//                            System.out.println("mylog/" + choices[1]);
//                            break;
//                    }
//                })
//                .setPositiveButton("OK", (dialogInterface, i) -> {
//                    //保存処理
//                })
//                .setNegativeButton("キャンセル",null);

//        return builder.create();
        return dialog;
    }

}
