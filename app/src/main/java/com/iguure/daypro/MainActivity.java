package com.iguure.daypro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.iguure.daypro.model.Diary;
import com.iguure.daypro.util.DataRetriever;
import com.iguure.daypro.util.DayNumToChar;
import com.iguure.daypro.util.DayNumToChar2;
import com.iguure.daypro.util.showData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner dateSelector;
    private Spinner monthSelector;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    private TextView addNewDiary;
    private Diary[] sod;

    private Calendar c;

    private int year;
    private int month;

    private boolean dateSelectorAs = true;
    private boolean monthSelectorAs = true;

    private String todayContent;

    private boolean viewAllToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // 获得当前时间（注意month比实际值小1）
        c = Calendar.getInstance();

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        dateSelector = (Spinner) findViewById(R.id.dateSelector);
        monthSelector = (Spinner) findViewById(R.id.monthSelector);

        // 数据
        data_list = new ArrayList<String>();

        // 生成最近7年的年份数值
        for (int i = 0; i < 7; i++) {
            data_list.add("" + (c.get(Calendar.YEAR) - i));
        }

        // 适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        dateSelector.setAdapter(arr_adapter);
        dateSelector.setSelection(0);
        monthSelector.setSelection(c.get(Calendar.MONTH));

        // 年份选择器
        dateSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (dateSelectorAs)
                    dateSelectorAs = false;
                else {

                    Calendar c = Calendar.getInstance();
                    Log.d("haha", "这个是年份" + (c.get(Calendar.YEAR) - i));
                    year = c.get(Calendar.YEAR) - i;

                    Diary[] d = (Diary[]) getObject(year + "-" + (month + 1) +".dat");

                    showData sd = new showData();
                    sd.show(MainActivity.this, MainActivity.this, d, year, month);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // 月份选择器
        monthSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (monthSelectorAs)
                    monthSelectorAs = false;
                else {

                    Log.d("haha", "这个是月份" + (i + 1));
                    month = i;

                    Diary[] d = (Diary[]) getObject(year + "-" + (month + 1) +".dat");

                    showData sd = new showData();
                    sd.show(MainActivity.this, MainActivity.this, d, year, month);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // 测试按钮
//        button = (Button) findViewById(R.id.testButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Log.d("Message","Clicked!");
////
////                Date d = new Date(2016, 9, 20);
////
////                sod = new Diary[2];
////                sod[0] = new Diary();
////                sod[0].setDate(d);
////                sod[0].setContent("这是9月20号的日记！");
////
////                d = new Date(2016, 9, 21);
////
////                sod[1] = new Diary();
////                sod[1].setDate(d);
////                sod[1].setContent("这个呢？是9月21号的日记!");
////
////                // 保存对象到本地
////                saveObject("2016-9.dat");
////
////                // 得到保存于本地路径的对象
////                Diary[] s = (Diary[]) getObject("2016-9.dat");
////
////                if (null != s) {
////                    for (int i = 0; i < s.length; i++) {
////                        System.out.println("日期：" + s[i].getDate().toString() + ", 内容：" + s[i].getContent() + "\n");
////                    }
////                } else {
////                    System.out.println("读取对象失败");
////                }
//
////                Diary[] d = (Diary[]) getObject("2016-9.dat");
////
////                DataRetriever dr = new DataRetriever();
////                List<Diary> diaryArrayList = dr.getDiaryArrayList(d, 2016, 9);
////                for (Diary diary : diaryArrayList) {
////                    Log.d("Important", diary.getDate().toString() + "+" + diary.getContent());
////                }
//
////                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main);
////                View diaryItem = View.inflate(MainActivity.this, R.layout.diary_item, null);
////                mainLayout.addView(diaryItem);
//            }
//        });

        // 首次启动时读取文件中当前月份的数据，并显示出来
        Diary[] d = (Diary[]) getObject(year + "-" + (month + 1) +".dat");

        showData sd = new showData();
        sd.show(MainActivity.this, this, d, year, month);

        // 获得当天的content，如果有的话
        todayContent = "";

        if (null != d) {
            for (int i = 0; i < d.length; i++) {
                if (d[i] != null) {
                    if (d[i].getDate().get(Calendar.DATE) == c.get(Calendar.DATE)) {
                        todayContent = d[i].getContent();
                    }
                }
            }
        }

        addNewDiary = (TextView) findViewById(R.id.addNewDiary);
        addNewDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("date", c.get(Calendar.DATE));
                intent.putExtra("content", todayContent);
                intent.putExtra("day", c.get(Calendar.DAY_OF_WEEK));
                startActivity(intent);
            }
        });



//        // 将数据存进系统的代码
//        sod = new Diary[1];
//
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, year);
//        cal.set(Calendar.MONTH, month);
//        cal.set(Calendar.DATE, 27);
//
//        sod[0] = new Diary();
//        sod[0].setDate(cal);
//        sod[0].setContent("这是9月27号的日记=。=.....");
//
//        // 保存对象到本地
//        saveObject("2016-9.dat");

        viewAllToggle = false;

        TextView viewAll = (TextView) findViewById(R.id.viewAll);
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main);

                Diary[] d = (Diary[]) getObject(year + "-" + (month + 1) +".dat");

                if (viewAllToggle) {
                    showData sd = new showData();
                    sd.show(MainActivity.this, MainActivity.this, d, year, month);
                    viewAllToggle = false;
                } else {
                    mainLayout.removeAllViews();
                    if (null != d) {
                        for (int i = 0; i < d.length; i++) {
                            if (d[i] != null) {
                                if (d[i].getContent() != null) {
                                    TextView textView = new TextView(MainActivity.this);
                                    textView.setText(d[i].getDate().get(Calendar.DATE) + " " + DayNumToChar2.transform(d[i].getDate().get(Calendar.DAY_OF_WEEK)) + "\n" + d[i].getContent() + "\n");
                                    mainLayout.addView(textView);
                                }
                            }
                        }
                    }
                    viewAllToggle = true;
                }
            }
        });

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
