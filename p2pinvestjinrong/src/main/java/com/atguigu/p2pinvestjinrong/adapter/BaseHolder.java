package com.atguigu.p2pinvestjinrong.adapter;

import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseHolder<T> {

    private View rootView;
    private T data;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this,rootView);
    }

    //提供item的布局
    public abstract View initView();

    public View getRootView() {
        return rootView;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }

    //装配过程
    public abstract void refreshData();
}
