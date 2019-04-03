package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : wanghailong
 * @description: 测试能滑动的控件
 */
public class NoScrollActivity extends BaseActivity {
    @BindView(R.id.tv_text_translationY)
    TextView mTvTextTranslationY;
    @BindView(R.id.btn_translationY_add)
    Button mBtnTranslationYAdd;
    @BindView(R.id.btn_translationY_reduce)
    Button mBtnTranslationYReduce;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NoScrollActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_noscroll;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.btn_translationY_add, R.id.btn_translationY_reduce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_translationY_add:
                mTvTextTranslationY.setTranslationY(10);
                break;
            case R.id.btn_translationY_reduce:
                mTvTextTranslationY.setTranslationY(-10);
                break;
        }
    }
}
