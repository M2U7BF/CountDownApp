package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
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
        System.out.println("mylog/" + "通知します");
        String[] choices = {"日前/後", "週間前/後"};
        String[] repeat = {"繰り返す"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        Dialog d = builder.setView(new View(this).create());
//        final NumberPicker numberPicker = new NumberPicker(getActivity());
        final EditText editTextNumber = new EditText(getActivity());

        final Switch repeatSwitch = new Switch(getActivity());
        repeatSwitch.setText("繰り返し");
        repeatSwitch.setGravity(Gravity.CENTER);

        final TextView showTime = new TextView(getActivity());

        builder.setTitle("カスタム通知")
                .setView(editTextNumber)
                .setSingleChoiceItems(choices,0,(dialogInterface, which) -> {
                    //選択時の挙動
                    switch (which) {
                        case 0:
                            System.out.println("mylog/" + choices[0]);
                            break;
                        case 1:
                            System.out.println("mylog/" + choices[1]);
                            break;
                    }
                })
                .setView(showTime)
                .setView(repeatSwitch)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    //保存処理
                })
                .setNegativeButton("キャンセル",null);

        return builder.create();
    }

}
