package com.atguigu.p2pinvestjinrong.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.common.ActivityManager;
import com.atguigu.p2pinvestjinrong.fragment.HomeFragment;
import com.atguigu.p2pinvestjinrong.fragment.InvestFragment;
import com.atguigu.p2pinvestjinrong.fragment.MeFragment;
import com.atguigu.p2pinvestjinrong.fragment.MoreFragment;
import com.atguigu.p2pinvestjinrong.util.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends FragmentActivity {


    @Bind(R.id.fl_main)
    FrameLayout flMain;
    @Bind(R.id.iv_main_home)
    ImageView ivMainHome;
    @Bind(R.id.tv_main_home)
    TextView tvMainHome;
    @Bind(R.id.ll_main_home)
    LinearLayout llMainHome;
    @Bind(R.id.iv_main_invest)
    ImageView ivMainInvest;
    @Bind(R.id.tv_main_invest)
    TextView tvMainInvest;
    @Bind(R.id.ll_main_invest)
    LinearLayout llMainInvest;
    @Bind(R.id.iv_main_me)
    ImageView ivMainMe;
    @Bind(R.id.tv_main_me)
    TextView tvMainMe;
    @Bind(R.id.ll_main_me)
    LinearLayout llMainMe;
    @Bind(R.id.iv_main_more)
    ImageView ivMainMore;
    @Bind(R.id.tv_main_more)
    TextView tvMainMore;
    @Bind(R.id.ll_main_more)
    LinearLayout llMainMore;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //将当前的activity添加到ActivityManager的栈列表中
        ActivityManager.getInstance().add(this);

        //默认显示首页
        setSelect(0);
    }

    @OnClick({R.id.ll_main_home, R.id.ll_main_invest, R.id.ll_main_me, R.id.ll_main_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home://首页
                setSelect(0);
                break;
            case R.id.ll_main_invest://投资
                setSelect(1);
                break;
            case R.id.ll_main_me://我的资产
                setSelect(2);
                break;
            case R.id.ll_main_more://更多
                setSelect(3);
                break;
        }
    }

    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;
    //提供相应的fragment的显示
    private void setSelect(int i) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();//获取碎片管理器
        transaction = fragmentManager.beginTransaction();//开启事务

        //隐藏所有Fragment的显示
        hideFragment();
        //重置ImageView和TextView的显示状态
        resetTab();
        switch (i) {
            case 0:
                if (homeFragment == null) {
                    homeFragment=new HomeFragment();//创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                    transaction.add(R.id.fl_main,homeFragment);//添加到事务中
                }
                //显示当前的fragment
                transaction.show(homeFragment);
                //改变选中项的图片和文本颜色的变化
                ivMainHome.setImageResource(R.drawable.bottom02);
//                tvMainHome.setTextColor(R.color.text_progress);//错误的写法
                //setTextColor()需要的是颜色ID，所以不能传一个颜色进去，根据颜色去寻找颜色ID，而是需要根据ID去找颜色
                tvMainHome.setTextColor(UIUtils.getColor(R.color.text_progress));
                break;

            case 1:
                if (investFragment == null) {
                    investFragment=new InvestFragment();//创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                    transaction.add(R.id.fl_main,investFragment);//添加到事务中
                }
                //显示当前的fragment
                transaction.show(investFragment);
                //改变选中项的图片和文本颜色的变化
                ivMainInvest.setImageResource(R.drawable.bottom04);
//                tvMainHome.setTextColor(R.color.text_progress);//错误的写法
                //setTextColor()需要的是颜色ID，所以不能传一个颜色进去，根据颜色去寻找颜色ID，而是需要根据ID去找颜色
                tvMainInvest.setTextColor(UIUtils.getColor(R.color.text_progress));
                break;

            case 2:
                if (meFragment == null) {
                    meFragment=new MeFragment();//创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                    transaction.add(R.id.fl_main,meFragment);//添加到事务中
                }
                //显示当前的fragment
                transaction.show(meFragment);
                //改变选中项的图片和文本颜色的变化
                ivMainMe.setImageResource(R.drawable.bottom06);
//                tvMainHome.setTextColor(R.color.text_progress);//错误的写法
                //setTextColor()需要的是颜色ID，所以不能传一个颜色进去，根据颜色去寻找颜色ID，而是需要根据ID去找颜色
                tvMainMe.setTextColor(UIUtils.getColor(R.color.text_progress));
                break;

            case 3:
                if (moreFragment == null) {
                    moreFragment=new MoreFragment();//创建对象以后，并不会马上调用生命周期方法。而是在commit()之后，方才调用
                    transaction.add(R.id.fl_main,moreFragment);//添加到事务中
                }
                //显示当前的fragment
                transaction.show(moreFragment);
                //改变选中项的图片和文本颜色的变化
                ivMainMore.setImageResource(R.drawable.bottom08);
//                tvMainHome.setTextColor(R.color.text_progress);//错误的写法
                //setTextColor()需要的是颜色ID，所以不能传一个颜色进去，根据颜色去寻找颜色ID，而是需要根据ID去找颜色
                tvMainMore.setTextColor(UIUtils.getColor(R.color.text_progress));
                break;
        }
        //提交事务
        transaction.commit();
    }

    private void resetTab() {
        ivMainHome.setImageResource(R.drawable.bottom01);
        ivMainInvest.setImageResource(R.drawable.bottom03);
        ivMainMe.setImageResource(R.drawable.bottom05);
        ivMainMore.setImageResource(R.drawable.bottom07);

        tvMainHome.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainInvest.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMe.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
        tvMainMore.setTextColor(UIUtils.getColor(R.color.home_back_unselected));
    }

    private void hideFragment() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
