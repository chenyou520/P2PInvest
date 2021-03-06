package com.atguigu.p2pinvestjinrong.fragment;

import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.adapter.ProductAdapter;
import com.atguigu.p2pinvestjinrong.adapter.ProductAdapter1;
import com.atguigu.p2pinvestjinrong.bean.Product;
import com.atguigu.p2pinvestjinrong.common.AppNetConfig;
import com.atguigu.p2pinvestjinrong.common.BaseFragment;
import com.atguigu.p2pinvestjinrong.ui.MyTextView;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

public class ProductListFragment extends BaseFragment {
    @Bind(R.id.tv_product_title)
    MyTextView tvProductTitle;
    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    private List<Product> productList;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    public void initData(String content) {
        //方式一：使得当前的textView获取焦点
//        tvProductTitle.setFocusable(true);
//        tvProductTitle.setFocusableInTouchMode(true);
//        tvProductTitle.requestFocus();
        //方式二：提供TextView的子类，重写isFocus(),返回true即可。

        JSONObject jsonObject = JSON.parseObject(content);
        boolean success = jsonObject.getBoolean("success");
        if (success) {
            String data = jsonObject.getString("data");
            //获取集合数据
            productList =jsonObject.parseArray(data,Product.class);

//            //方式二：抽取了，但是抽取力度小 （可以作为选择）
//            ProductAdapter productAdapter = new ProductAdapter(productList);
//            lvProductList.setAdapter(productAdapter);//显示列表

            //方式四：抽取了，最好的方式.（可以作为选择）
            ProductAdapter1 productAdapter1 = new ProductAdapter1(productList);
            lvProductList.setAdapter(productAdapter1);
        }
    }

    @Override
    public void initTitle() {

    }

    @Override
    public String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_productlist;
    }
}
