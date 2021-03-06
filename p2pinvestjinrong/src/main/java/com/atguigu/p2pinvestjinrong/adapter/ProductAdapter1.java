package com.atguigu.p2pinvestjinrong.adapter;

import com.atguigu.p2pinvestjinrong.bean.Product;

import java.util.List;

public class ProductAdapter1 extends MyBaseAdapter1<Product> {
    public ProductAdapter1(List<Product> list) {
        super(list);
    }

    @Override
    public BaseHolder<Product> getHolder() {
        return new MyHolder();
    }
}
