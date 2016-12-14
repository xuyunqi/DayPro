package com.iguure.daypro.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 9/25 0025.
 */
public class Diary implements Serializable {

    private static final long serialVersionUID = 1L;

    private Calendar cal;

    private String content;

    public Calendar getDate() {
        return cal;
    }

    public void setDate(Calendar cal) {
        this.cal = cal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
