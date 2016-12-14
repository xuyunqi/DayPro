package com.iguure.daypro.util;

import android.app.Activity;
import android.util.Log;

import com.iguure.daypro.model.Diary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 9/27 0027.
 */
public class DataRetriever extends Activity {

    List<Diary> diaryArrayList = new ArrayList();

    public List<Diary> getDiaryArrayList(Diary[] s, int year, int month) {

        // 获得这个月的天数
        int days = CountDays.count(year, month);

        Log.d("天数", days + "");

        // 为每一天创建一个Diary类
        // 每个List<Diary>类有n（天数）个元素，通过Calendar变量注明时间
        // 如果某一天在数据中有数据，则将数据中的content写进该类对应天的content中
        for (int i = 1; i <= days; i++) {
            Diary diary = new Diary();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DATE, i);
            diary.setDate(cal);
            if (s != null) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j] != null) {
                        if (s[j].getDate().get(Calendar.DATE) == i) {
                            diary.setContent(s[j].getContent());
                            break;
                        }
                    }
                }
            }
            diaryArrayList.add(diary);
        }

        return diaryArrayList;

    }

}
