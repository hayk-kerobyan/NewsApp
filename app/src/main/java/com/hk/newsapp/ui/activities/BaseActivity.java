package com.hk.newsapp.ui.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.hk.newsapp.NetworkManger;
import com.hk.newsapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public abstract class BaseActivity extends AppCompatActivity {

    protected BroadcastReceiver networkReceiver;
    public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private Toast toast;
    private ProgressDialog progressDialog;

    //TODO: Inject with Dagger
    private NetworkManger networkManger = new NetworkManger(getApplication());

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

    public boolean isConnected() {
        return networkManger.isConnected();
    }

    protected abstract void onNetworkStateChanged(boolean isConnected);


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
