package com.testanim.wanghailong.testpropertyanim.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.customerClass.LuckyPan;

public class LuckyPanActivity extends AppCompatActivity {

    private LuckyPan mLuckyPan;
    private ImageView mIvStartStop;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LuckyPanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_pan);
        mLuckyPan = findViewById(R.id.luckyPan);
        mIvStartStop = findViewById(R.id.iv_start_stop);
        mIvStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLuckyPan.isStart()) {
                    mLuckyPan.luckyPanStart();
                    mIvStartStop.setImageResource(R.drawable.stop);
                } else {
                    //如果此时转盘还在旋转且停止按钮已经按过了，那么调用luckyPanStop()
                    if (!mLuckyPan.isShouldEnd()) {
                        mLuckyPan.luckyPanStop();
                        mIvStartStop.setImageResource(R.drawable.start);
                    }
                }
            }
        });

    }
}
