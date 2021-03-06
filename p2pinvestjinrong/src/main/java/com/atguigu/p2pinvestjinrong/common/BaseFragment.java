package com.atguigu.p2pinvestjinrong.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.atguigu.p2pinvestjinrong.ui.LoadingPage;
import com.atguigu.p2pinvestjinrong.util.UIUtils;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
//        View view = UIUtils.getView(getLayoutId());
//        ButterKnife.bind(this, view);
//        initTitle();
//        initData();
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected void onSuccss(ResultState resultState, View view_success) {
                ButterKnife.bind(BaseFragment.this, view_success);
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            public String url() {
                return getUrl();
            }
        };
        return loadingPage;
    }

    //为了保证loadingPage不为null
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //模拟联网操作的延迟
//        UIUtils.getHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                show();
//            }
//        },2000);
        show();
    }

    private void show() {
        loadingPage.show();
    }

    protected abstract RequestParams getParams();


    //初始化界面的数据
    public abstract void initData(String content);

    //初始化title
    public abstract void initTitle();

    public abstract String getUrl();

    //提供布局
    public abstract int getLayoutId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
