package com.example.countdownapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MySimpleAdapter extends SimpleAdapter {
    private LayoutInflater inflater;
    private List<? extends Map<String, ?>> listData;
    Common c;

    public static class ViewHolder {
        TextView line1;
        TextView line2;
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        // TODO 自動生成されたコンストラクター・スタブ
        // this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = data;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // ビューを受け取る
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.listview_row, parent, false);

            holder = new ViewHolder();
            holder.line1 = view.findViewById(R.id.row_textView_title);
            holder.line2 = view.findViewById(R.id.row_textView_text);
            holder.textView1 = view.findViewById(R.id.textView3);
            holder.textView2 = view.findViewById(R.id.textView2);
            holder.textView3 = view.findViewById(R.id.textView4);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ImageButton btn = view.findViewById(R.id.button_notification_setting);
        btn.setOnClickListener( v -> {
            MainActivity activity = (MainActivity) v.getContext();
            c = new Common();
            //https://weblog.hirohiro716.com/?p=244
            System.out.println("mylog/"+ "Adapterでのポジションは : " + position);
//            c.clickedPosition = position;

            activity.showNotificationDialog(v,position);
        });

        String text1 = Objects.requireNonNull(((HashMap<?, ?>) listData.get(position)).get("title")).toString();
        String text2 = Objects.requireNonNull(((HashMap<?, ?>) listData.get(position)).get("startDate")).toString();
        String text3 = Objects.requireNonNull(((HashMap<?, ?>) listData.get(position)).get("text3")).toString();
        String text4 = Objects.requireNonNull(((HashMap<?, ?>) listData.get(position)).get("difference_of_days")).toString();
        String text5 = Objects.requireNonNull(((HashMap<?, ?>) listData.get(position)).get("text4")).toString();
        holder.line1.setText(text1);
        holder.line2.setText(text2);
        holder.textView1.setText(text3);
        holder.textView2.setText(text4);
        holder.textView3.setText(text5);

        return view;
    }
}
