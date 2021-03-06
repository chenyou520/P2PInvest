package com.atguigu.p2pinvestjinrong.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.common.BaseFragment;
import com.atguigu.p2pinvestjinrong.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class InvestFragment extends BaseFragment {


    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @Bind(R.id.tabpage_invest)
    TabPageIndicator tabpageInvest;
    @Bind(R.id.vp_invest)
    ViewPager vpInvest;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    public void initData(String content) {
        //1.加载三个不同的Fragment：ProductListFragment,ProductRecommondFragment,ProductHotFragment.
        initFragments();
        //2.ViewPager设置三个Fragment的显示
        MyAdapter myAdapter = new MyAdapter(getFragmentManager());
        vpInvest.setAdapter(myAdapter);
        //将TabPagerIndicator与ViewPager关联
        tabpageInvest.setViewPager(vpInvest);
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    private void initFragments() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        //添加到集合中
        fragmentList.add(productListFragment);
        fragmentList.add(productRecommondFragment);
        fragmentList.add(productHotFragment);
    }

    @Override
    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("投资");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    /**
     * 提供PagerAdapter的实现
     * 如果ViewPager中加载的是Fragment,则提供的Adpater可以继承于具体的：FragmentStatePagerAdapter或FragmentPagerAdapter
     * FragmentStatePagerAdapter:适用于ViewPager中加载的Fragment过多，会根据最近最少使用算法，实现内存中Fragment的清理，避免溢出
     * FragmentPagerAdapter:适用于ViewPager中加载的Fragment不多时，系统不会清理已经加载的Fragment。
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList == null ? 0 : fragmentList.size();
        }

        //提供TabPageIndicator的显示内容

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return UIUtils.getStringArr(R.array.invest_tab)[position];
        }
    }

}
