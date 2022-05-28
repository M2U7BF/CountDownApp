package com.example.countdownapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Collections;
import java.util.Comparator;

public class MyDialogFragment extends DialogFragment {
    Common c;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] choices = {"期日順(デフォルト)","タイトル順"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("並び替え")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                })
                .setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        c = (Common) getActivity().getApplication();

                        if (which == 0) {
                            c.sortSwitch_deadline = true;
                            c.sortSwitch_title = false;
                        } else if (which == 1) {
                            c.sortSwitch_deadline = false;
                            c.sortSwitch_title = true;
                        }
                        System.out.println("mylog/" + choices[which]);

                        c.sortList();

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }
}
