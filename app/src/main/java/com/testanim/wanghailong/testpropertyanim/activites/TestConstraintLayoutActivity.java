package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @author : wanghailong
 * @description:
 */
public class TestConstraintLayoutActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TestConstraintLayoutActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_constratin;
    }

    @Override
    protected void init() {

    }
}
