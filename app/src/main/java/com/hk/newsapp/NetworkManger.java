package com.hk.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NetworkManger {

    @Inject
    public NetworkManger(Context context) {
        this.context = context;
    }

    private Context context;

    public boolean isConnected() {
        try {
            ConnectivityManager cm
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
