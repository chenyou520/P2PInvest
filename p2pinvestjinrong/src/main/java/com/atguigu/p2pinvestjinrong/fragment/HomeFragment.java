package com.atguigu.p2pinvestjinrong.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.bean.Image;
import com.atguigu.p2pinvestjinrong.bean.Index;
import com.atguigu.p2pinvestjinrong.bean.Product;
import com.atguigu.p2pinvestjinrong.common.AppNetConfig;
import com.atguigu.p2pinvestjinrong.common.BaseFragment;
import com.atguigu.p2pinvestjinrong.ui.RoundProgress;
import com.atguigu.p2pinvestjinrong.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    @Bind(R.id.iv_title_back)
    ImageView ivTitleBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView ivTitleSetting;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.tv_home_product)
    TextView tvHomeProduct;
    @Bind(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @Bind(R.id.roundPro_home)
    RoundProgress roundProHome;
    private Index index;
    private int currentProgress;//服务器的进度
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            roundProHome.setMax(100);
            for (int i = 0; i < currentProgress; i++) {
                roundProHome.setProgress(i + 1);
                SystemClock.sleep(20);
                //强制重绘
//                roundProHome.invalidate();//只有主线程才可以如此调用
                roundProHome.postInvalidate();//主线程、分线程都可以如此调用
            }
        }
    };

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
//        ButterKnife.bind(this, view);
//        initTitle();
//        initData();
//        return view;
//    }

//    public void initData() {
//        index = new Index();
//        AsyncHttpClient client = new AsyncHttpClient();
//        //访问的url
//        String url = AppNetConfig.INDEX;
//        client.post(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(String content) {//200：响应成功
//                //解析json数据：GSON / FASTJSON
//                JSONObject jsonObject = JSON.parseObject(content);
//                //解析json对象数据
//                String proInfo = jsonObject.getString("proInfo");
//                Product product = JSON.parseObject(proInfo, Product.class);
//                //解析json数组数据
//                String imageArr = jsonObject.getString("imageArr");
//                List<Image> images = jsonObject.parseArray(imageArr, Image.class);
//                index.product = product;
//                index.images = images;
//
//
//                //更新页面数据
//                tvHomeProduct.setText(product.name);
//                tvHomeYearrate.setText(product.yearRate + "%");
//                //获取数据中的进度值
//                currentProgress = Integer.parseInt(index.product.progress);
//                //在分线程中，实现进度的动态变化
//                new Thread(runnable).start();
//
//                //设置banner样式
//                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//                //设置图片加载器
//                banner.setImageLoader(new GlideImageLoader());
//                //设置图片地址构成的集合
//                ArrayList<String> imagesUrl = new ArrayList<>(index.images.size());
//                for (int i = 0; i < index.images.size(); i++) {//index.images.size():4
//                    imagesUrl.add(index.images.get(i).IMAURL);
//                }
//                banner.setImages(imagesUrl);
//                //设置banner动画效果
//                banner.setBannerAnimation(Transformer.DepthPage);
//                //设置标题集合（当banner样式有显示title时）
//                String[] titles = new String[]{"分享砍学费", "人脉总动员", "想不到你是这样的app", "购物节，爱不单行"};
//                banner.setBannerTitles(Arrays.asList(titles));
//                //设置自动轮播，默认为true
//                banner.isAutoPlay(true);
//                //设置轮播时间
//                banner.setDelayTime(1500);
//                //设置指示器位置（当banner模式中有指示器时）
//                banner.setIndicatorGravity(BannerConfig.CENTER);
//                //banner设置方法全部调用完毕时最后调用
//                banner.start();
//            }
//
//            @Override
//            public void onFailure(Throwable error, String content) {//响应失败
//                Toast.makeText(UIUtils.getContext(), "联网获取数据失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    public void initData(String content) {
        if (!TextUtils.isEmpty(content)) {
            index = new Index();
            //解析json数据：GSON / FASTJSON
            JSONObject jsonObject = JSON.parseObject(content);
            //解析json对象数据
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            //解析json数组数据
            String imageArr = jsonObject.getString("imageArr");
            List<Image> images = jsonObject.parseArray(imageArr, Image.class);
            index.product = product;
            index.images = images;

            //更新页面数据
            tvHomeProduct.setText(product.name);
            tvHomeYearrate.setText(product.yearRate + "%");
            //获取数据中的进度值
            currentProgress = Integer.parseInt(index.product.progress);

            //在分线程中，实现进度的动态变化
            new Thread(runnable).start();


            //设置banner样式
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());
            //设置图片地址构成的集合
            ArrayList<String> imagesUrl = new ArrayList<String>(index.images.size());
//                for(int i = 0; i < imagesUrl.size(); i++) {//imagesUrl.size():0
            for (int i = 0; i < index.images.size(); i++) {//index.images.size():4
                imagesUrl.add(index.images.get(i).IMAURL);
            }
            banner.setImages(imagesUrl);
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            String[] titles = new String[]{"分享砍学费", "人脉总动员", "想不到你是这样的app", "购物节，爱不单行"};
            banner.setBannerTitles(Arrays.asList(titles));
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
        }
    }

    public void initTitle() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitle.setText("首页");
        ivTitleSetting.setVisibility(View.GONE);
    }

    @Override
    public String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        ButterKnife.unbind(this);
//    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */


            //Picasso 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);
        }

    }
}
