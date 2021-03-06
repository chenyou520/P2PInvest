package com.atguigu.p2pinvestjinrong.adapter;

import android.view.View;
import android.widget.TextView;

import com.atguigu.p2pinvestjinrong.R;
import com.atguigu.p2pinvestjinrong.bean.Product;
import com.atguigu.p2pinvestjinrong.ui.RoundProgress;
import com.atguigu.p2pinvestjinrong.util.UIUtils;

import butterknife.Bind;

public class MyHolder extends BaseHolder<Product> {
    @Bind(R.id.p_name)
    TextView pName;
    @Bind(R.id.p_money)
    TextView pMoney;
    @Bind(R.id.p_yearlv)
    TextView pYearlv;
    @Bind(R.id.p_suodingdays)
    TextView pSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView pMinzouzi;
    @Bind(R.id.p_minnum)
    TextView pMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress pProgresss;

    @Override
    public View initView() {
        return View.inflate(UIUtils.getContext(), R.layout.item_product_list, null);
    }

    @Override
    public void refreshData() {
        //装配数据
        Product data = this.getData();
        pMinnum.setText(data.memberNum);
        pMinzouzi.setText(data.minTouMoney);
        pMoney.setText(data.money);
        pName.setText(data.name);
        pProgresss.setProgress(Integer.parseInt(data.progress));
        pSuodingdays.setText(data.suodingDays);
        pYearlv.setText(data.yearRate);

    }
}
