package com.hk.newsapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkManger {

    public NetworkManger(Application application) {
        this.application = application;
    }

    private Application application;

    public boolean isConnected() {
        try {
            ConnectivityManager cm
                    = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getActiveNetworkInfo();
            }
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
