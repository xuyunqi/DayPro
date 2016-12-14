package com.iguure.daypro.util;

/**
 * Created by Administrator on 9/27 0027.
 */
public class DayNumToChar2 {

    // 将数字表示的星期转换成全程大写字母
    public static String transform(int num) {
        switch (num) {
            case 7:
                return "SATURDAY";
            case 1:
                return "SUNDAY";
            case 2:
                return "MONDAY";
            case 3:
                return "TUESDAY";
            case 4:
                return "WEDNESDAY";
            case 5:
                return "THURSDAY";
            case 6:
                return "FRIDAY";
            default:
                return "";
        }
    }
}
