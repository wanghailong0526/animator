package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @author : wanghailong
 * @description:
 */
public class TestScrollerActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TestScrollerActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_scroller;
    }

    @Override
    protected void init() {

    }
}
