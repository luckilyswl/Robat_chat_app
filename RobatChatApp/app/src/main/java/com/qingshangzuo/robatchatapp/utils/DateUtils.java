package com.qingshangzuo.robatchatapp.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;


/*
  时间类
 */
public class DateUtils {

    @SuppressLint("SimpleDateFormat")
    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        return df.format(date);
    }
}
