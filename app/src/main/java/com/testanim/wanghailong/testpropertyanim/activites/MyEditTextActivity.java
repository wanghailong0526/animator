package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.base.BaseActivity;

/**
 * @author : wanghailong
 * @description:
 */
public class MyEditTextActivity extends BaseActivity {
    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MyEditTextActivity.class);
            context.startActivity(intent);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_edit_text;
    }

    @Override
    protected void init() {

    }
}
