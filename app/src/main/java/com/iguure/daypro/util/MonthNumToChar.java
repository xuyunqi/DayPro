package com.iguure.daypro.util;

/**
 * Created by Administrator on 9/27 0027.
 */
public class MonthNumToChar {

    // 将数字表示的月份转换成全称大写字母
    public static String transform(int num) {
        switch (num) {
            case 0:
                return "JANUARY";
            case 1:
                return "FEBRUARY";
            case 2:
                return "MARCH";
            case 3:
                return "APRIL";
            case 4:
                return "MAY";
            case 5:
                return "JUNE";
            case 6:
                return "JULY";
            case 7:
                return "AUGUST";
            case 8:
                return "SEPTEMBER";
            case 9:
                return "OCTOBER";
            case 10:
                return "NOVEMBER";
            case 11:
                return "DECEMBER";
            default:
                return "";
        }
    }
}
