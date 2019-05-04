package com.testanim.wanghailong.testdoordinatorlayout.testNestedScrollView;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @date : 2019/4/28 下午11:50
 * @author: wanghailong
 * @description:
 */

public class NestedScrollViewActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, NestedScrollViewActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_nested_scroll_view;
    }

    @Override
    protected void init() {

    }
}
