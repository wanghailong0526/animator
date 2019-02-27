package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @author : wanghailong
 * @description: 测试能滑动的控件
 */
public class NoScrollActivity extends BaseActivity {
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
}
