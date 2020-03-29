package com.testanim.wanghailong.testpropertyanim.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @author : wanghailong
 * @date :
 * @description:
 */
public class FlowLayoutActivity extends Activity {
    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, FlowLayoutActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_flow_layout);
        View viewById = findViewById(R.id.btn_first);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FlowLayoutActivity.this, "点击了…………", Toast.LENGTH_LONG).show();
            }
        });
    }
}
