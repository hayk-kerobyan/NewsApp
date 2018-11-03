package com.hk.newsapp.utils;

import android.os.Build;
import android.text.Html;

public class TextUtils {

    @SuppressWarnings("deprecation")
    public static String htmlToString(String html){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }
}
