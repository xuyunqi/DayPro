package com.iguure.daypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iguure.daypro.model.Diary;
import com.iguure.daypro.util.DayNumToChar2;
import com.iguure.daypro.util.MonthNumToChar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class DiaryDetailActivity extends AppCompatActivity {

    private Diary[] sod;
    private TextView done;

    private int y;
    private int m;
    private int d;
    private String c;
    private String cc;
    private TextView contentTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_diary_detail);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int date = intent.getIntExtra("date", 0);
        int day = intent.getIntExtra("day", 0);
        String content = intent.getStringExtra("content");

        TextView yearTV = (TextView) findViewById(R.id.year);
        yearTV.setText(year + "");
        TextView monthTV = (TextView) findViewById(R.id.month);
        monthTV.setText(MonthNumToChar.transform(month));
        TextView dateTV = (TextView) findViewById(R.id.date);
        dateTV.setText(date + "");
        TextView dayTV = (TextView) findViewById(R.id.day);
        dayTV.setText(DayNumToChar2.transform(day));
        contentTV = (TextView) findViewById(R.id.content);
        contentTV.setText(content);

        y = year;
        m = month;
        d = date;
        c = content;

        sod = (Diary[]) getObject(year + "-" + (month + 1) +".dat");

        done = (TextView) findViewById(R.id.done);

        // 按钮none的各种操作
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cc = contentTV.getText().toString();
                if (!cc.isEmpty()) {
                    if (null != sod) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y, m, d);
                        sod[d] = new Diary();
                        sod[d].setDate(calendar);
                        sod[d].setContent(cc);
                    }
                    else {
                        sod = new Diary[31];
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y, m, d);
                        sod[d] = new Diary();
                        sod[d].setDate(calendar);
                        sod[d].setContent(cc);
                    }
                } else {
                    if (null != sod) {
                        if (sod[d].getContent() != null) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(y, m, d);
                            sod[d] = new Diary();
                            sod[d].setDate(calendar);
                            sod[d].setContent(null);
                        }
                    }
                }

                saveObject(y + "-" + (m + 1) + ".dat");

                Intent intent = new Intent(DiaryDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sod);
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    // fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    // oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            // 这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    // fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    // ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        // 读取产生异常，返回null
        return null;
    }
}
