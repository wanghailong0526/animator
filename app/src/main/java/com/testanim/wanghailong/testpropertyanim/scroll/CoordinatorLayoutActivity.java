package com.testanim.wanghailong.testpropertyanim.scroll;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @author : wanghailong
 * @description:
 */
public class CoordinatorLayoutActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, CoordinatorLayoutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coordinatorlayout;
    }

    @Override
    protected void init() {

    }
}
