package com.example.countdownapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class ActivitySetData extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Button Button_set_date;
    Button Button_save;
    private TextView textView_show_date;
    private EditText editText_title;
    Common c;

    private boolean DataSaveOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);

        getWindow().setStatusBarColor(Color.DKGRAY);

        findViews();
        if(savedInstanceState == null) {
            setListeners();
        }
    }

    protected void findViews(){
        Button_save = findViewById(R.id.button_save);
        textView_show_date = findViewById(R.id.texView_show_date);
        editText_title = findViewById(R.id.editText_title);
    }

    protected void setListeners(){
        Button_save.setOnClickListener( v -> {

            //値をセット
            c = (Common)getApplication();

            String title = editText_title.getText().toString();
            String startDate = textView_show_date.getText().toString();

            //保存のためのvalidation
            if(startDate.isEmpty()){
                noInputsChecker();
            }else{
                DataSaveOK = true;
            }

            //最終的な保存
            if(DataSaveOK) {

                if(title.isEmpty()) {
                    ++c.elem_num;
                    title = "無題" + c.elem_num;
                }

                //完成したデータセットを追加
                c.dataset.add(new CompareObjects(title,startDate));
                c.sortList();

                //画面遷移
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String str = String.format(Locale.US, "%d/%d/%d",year,month+1,day);
        textView_show_date.setText(str);
    }

    private void noInputsChecker(){
        String toastMessage = "開始日が設定されていません";
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

}