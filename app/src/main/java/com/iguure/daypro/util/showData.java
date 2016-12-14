package com.iguure.daypro.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iguure.daypro.DiaryDetailActivity;
import com.iguure.daypro.MainActivity;
import com.iguure.daypro.R;
import com.iguure.daypro.model.Diary;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 9/27 0027.
 */
public class showData {

    private Context context;
    private int y;
    private int m;

    // 显示数据
    public void show(Context c, Activity a, Diary[] d, int year, int month) {

        context = c;
        y = year;
        m = month;

        DataRetriever dr = new DataRetriever();
        List<Diary> diaryArrayList = dr.getDiaryArrayList(d, year, month);

        LinearLayout mainLayout = (LinearLayout) a.findViewById(R.id.main);

        mainLayout.removeAllViews();

        // 为每一个数据设置监听器
        for (Diary diary : diaryArrayList) {
            if (diary.getContent() != null) {
                View diaryItem = View.inflate(c, R.layout.diary_item, null);
                TextView day = (TextView) diaryItem.findViewById(R.id.day);
                TextView date = (TextView) diaryItem.findViewById(R.id.date);
                TextView content = (TextView) diaryItem.findViewById(R.id.content);
                day.setText(DayNumToChar.transform(diary.getDate().get(Calendar.DAY_OF_WEEK)));
                date.setText(diary.getDate().get(Calendar.DATE) + "");
                content.setText(diary.getContent());
                final int da =  diary.getDate().get(Calendar.DATE);
                final int da2 = diary.getDate().get(Calendar.DAY_OF_WEEK);
                final String con = diary.getContent();
                diaryItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(context, DiaryDetailActivity.class);
                            intent.putExtra("year", y);
                            intent.putExtra("month", m);
                            intent.putExtra("date", da);
                            intent.putExtra("day", da2);
                            intent.putExtra("content", con);
                            context.startActivity(intent);
                    }
                });
                mainLayout.addView(diaryItem);
            } else {
                View pointItem = View.inflate(c, R.layout.point_item, null);
                TextView point = (TextView) pointItem.findViewById(R.id.point);
                if (diary.getDate().get(Calendar.DAY_OF_WEEK) == 1)
                    point.setTextColor(Color.rgb(255, 0, 0));
                final int da =  diary.getDate().get(Calendar.DATE);
                final int da2 = diary.getDate().get(Calendar.DAY_OF_WEEK);
                pointItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, DiaryDetailActivity.class);
                        intent.putExtra("year", y);
                        intent.putExtra("month", m);
                        intent.putExtra("date", da);
                        intent.putExtra("day", da2);
                        intent.putExtra("content", "");
                        context.startActivity(intent);
                    }
                });
                mainLayout.addView(pointItem);
            }
        }

    }

}
