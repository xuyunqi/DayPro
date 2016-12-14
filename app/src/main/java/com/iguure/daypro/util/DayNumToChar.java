package com.iguure.daypro.util;

/**
 * Created by Administrator on 9/27 0027.
 */
public class DayNumToChar {

    // 将数字表示的星期转换成大写前三个字母
    public static String transform(int num) {
        switch (num) {
            case 7:
                return "SAT";
            case 1:
                return "SUN";
            case 2:
                return "MON";
            case 3:
                return "TUE";
            case 4:
                return "WED";
            case 5:
                return "THU";
            case 6:
                return "FRI";
            default:
                return "";
        }
    }
}
