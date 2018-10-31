package com.hk.newsapp.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.hk.newsapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {

    protected BroadcastReceiver networkReceiver;
    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toast toast;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       /* if (getResources().getBoolean(R.bool.landscape_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }*/
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterNetworkReceiver();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag,
                               boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, fragmentTag);
        if (addToBackStack)
            fragmentTransaction.addToBackStack(fragmentTag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String fragmentTag,
                                   boolean addToBackStack) {
        if (isConnected()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragmentTag);
            }
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            showNetworkConnectionFailure();
        }
    }

    private void registerNetworkReceiver() {
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null
                        && intent.getAction().equals(CONNECTIVITY_CHANGE_ACTION)) {
                    onNetworkStateChanged(isConnected());
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(CONNECTIVITY_CHANGE_ACTION);
        registerReceiver(networkReceiver, intentFilter);

    }

    private void unregisterNetworkReceiver() {
        try {
            unregisterReceiver(networkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected abstract void onNetworkStateChanged(boolean isConnected);

    public boolean isConnected() {
        try {
            ConnectivityManager cm
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void showNetworkConnectionFailure() {
        showToast(getString(R.string.turn_on_internet_message));
    }

    protected void showRequestError() {
        showToast(getString(R.string.connection_failure_message));
    }

    public void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showProgressDialog() {
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        mProgressDialog.dismiss();
    }


    public void disableUserActions() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableUserActions() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
