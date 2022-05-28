package com.example.countdownapp;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Locale;

public class ActivityEditListElem extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText editText_title;
    TextView textView_show_date;
    Button button_set_date;
    Button button_save;
    Button button_delete;
    Button button_set_notification;

    private boolean DataSaveOK = false;

    Common c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_elem);

        getWindow().setStatusBarColor(Color.DKGRAY);
        c = (Common) getApplication();

        findViews();

        CompareObjects d = (CompareObjects) c.dataset.get(c.editPosition);

        //値受け取り
        String selectedText = d.getTitle();
        String selectedDate = d.getStartDate();
        editText_title.setText(selectedText);
        textView_show_date.setText(selectedDate);

        setListeners();
    }

    protected void findViews(){
        editText_title = findViewById(R.id.textView_set_title);
        textView_show_date = findViewById(R.id.texView_show_date);
//        button_set_date = findViewById(R.id.button_set_date);
        button_save = findViewById(R.id.button_save);
        button_delete = findViewById(R.id.button_delete);
//        button_set_notification = findViewById(R.id.button_set_notification);
    }

    protected void setListeners(){
        button_save.setOnClickListener( v -> {

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

                //タイトルが空白の場合の処理
                if(title.isEmpty()) {
                    ++c.elem_num;
                    title = "無題" + c.elem_num;
                }

                c.dataset.set(c.editPosition,new CompareObjects(title,startDate));

                c.sortList();

                //画面遷移
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        });
        button_delete.setOnClickListener( w -> {
            c.dataset.remove(c.editPosition);

            c.sortList();

            //画面遷移
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String str = String.format(Locale.JAPAN, "%d/%d/%d",year,month+1,day);
        textView_show_date.setText(str);
    }

    public void noInputsChecker(){
        String toastMessage = "開始日が設定されていません";
        Toast toast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        toast.show();
    }
}