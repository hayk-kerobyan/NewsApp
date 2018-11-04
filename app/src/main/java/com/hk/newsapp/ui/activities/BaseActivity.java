package com.hk.newsapp.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hk.newsapp.R;
import com.hk.newsapp.managers.NetworkManger;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    protected BroadcastReceiver networkReceiver;
    public static final int CONNECTED_STATE_MESSAGE_DELAY = 1000;
    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private Handler handler;
    private Toast toast;
    private ProgressDialog progressDialog;
    protected TextView networkStateTV;

    @Inject
    NetworkManger networkManger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getResources().getBoolean(R.bool.landscape_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onCreate(savedInstanceState);
        setUpProgressBar();
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
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            networkStateTV.setVisibility(View.GONE);
            handler = null;
        }
    };

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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragmentTag);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentTag);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void registerNetworkReceiver() {
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null
                        && intent.getAction().equals(CONNECTIVITY_CHANGE_ACTION)) {
                    handleNetworkStateChanged(isConnected());
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

    public boolean isConnected() {
        return networkManger.isConnected();
    }

    private void handleNetworkStateChanged(boolean isConnected) {
        if(networkStateTV!=null) {
            if (isConnected) {
                setConnectedState();
            } else {
                setDisconnectedState();
            }
        }
        onNetworkStateChanged(isConnected);
    }

    protected abstract void onNetworkStateChanged(boolean isConnected);

    private void setConnectedState() {
        networkStateTV.setText(R.string.connected);
        networkStateTV.setBackgroundColor(getResources().getColor(R.color.bg_network_connected_tv));
        if (handler != null) {
            handler.removeCallbacks(runnable);
        } else {
            handler = new Handler();
        }
        handler.postDelayed(runnable, CONNECTED_STATE_MESSAGE_DELAY);
    }

    private void setDisconnectedState() {
        networkStateTV.setVisibility(View.VISIBLE);
        networkStateTV.setText(R.string.disconnected);
        networkStateTV.setBackgroundColor(getResources().getColor(R.color.bg_network_disconnected_tv));
    }

    public void showNetworkConnectionFailure() {
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

    protected void setUpProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    public void showProgressDialog(String message) {
        progressDialog.setMessage(message != null
                ? message : getString(R.string.wait_message));
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }


    public void disableUserActions() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableUserActions() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
