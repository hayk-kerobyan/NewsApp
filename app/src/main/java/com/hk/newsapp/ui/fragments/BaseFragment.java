package com.hk.newsapp.ui.fragments;

import android.content.Context;

import com.hk.newsapp.ui.activities.BaseActivity;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment extends DaggerFragment {


    private BaseActivity mBaseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mBaseActivity = (BaseActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must extend BaseActivity");
        }
    }

    @Override
    public void onDetach() {
        mBaseActivity = null;
        super.onDetach();
    }

    protected void showToast(String message) {
        mBaseActivity.showToast(message);
    }

    protected void showProgressDialog(String message) {
        mBaseActivity.showProgressDialog(message);
    }

    protected void dismissProgressDialog() {
        mBaseActivity.dismissProgressDialog();
    }
}