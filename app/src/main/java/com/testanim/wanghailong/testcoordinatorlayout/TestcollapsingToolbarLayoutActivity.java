package com.testanim.wanghailong.testcoordinatorlayout;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.activites.MyEditTextActivity;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @date : 2019-08-19 17:13
 * @author: wanghailong
 * @description:
 */
public class TestcollapsingToolbarLayoutActivity extends BaseActivity {
    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, TestcollapsingToolbarLayoutActivity.class);
            context.startActivity(intent);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.test_collaps_toolbar_layout;
    }

    @Override
    protected void init() {

    }
}
