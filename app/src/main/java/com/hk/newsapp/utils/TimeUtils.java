package com.hk.newsapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static final String DEFAULT_DATE_FORMAT = "MMMM dd, yyyy";

    public static String dateToString(Date date) {
        SimpleDateFormat simpleDate = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        return simpleDate.format(date);
    }
}
