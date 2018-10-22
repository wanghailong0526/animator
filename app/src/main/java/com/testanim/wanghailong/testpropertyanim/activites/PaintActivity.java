package com.testanim.wanghailong.testpropertyanim.activites;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.customerClass.BatteryImageView2;
import com.testanim.wanghailong.testpropertyanim.customerClass.WaveView;
import com.testanim.wanghailong.testpropertyanim.customerClass.paint.ArcProgress;
import com.testanim.wanghailong.testpropertyanim.customerClass.paint.ArcScanView;
import com.testanim.wanghailong.testpropertyanim.customerClass.paint.RadarView;
import com.testanim.wanghailong.testpropertyanim.customerClass.paint.RadarView1;

public class PaintActivity extends AppCompatActivity {
    private int i = 0;
    private @SuppressLint("HandlerLeak")
    Handler handler;
    private BatteryImageView2 mBatteryView;
    private WaveView mWaveView;
    private ArcScanView mArcScanView;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PaintActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pait);

        mArcScanView = findViewById(R.id.arc_scanview);
        mBatteryView = findViewById(R.id.battery_view);
        mWaveView = findViewById(R.id.waveView);
        RadarView1 mRadarView = findViewById(R.id.randerViewPoint);
        RadarView mRadarViewSelf = findViewById(R.id.radarView);
        ArcProgress mArcProgress = findViewById(R.id.arcprogress);
        mArcProgress.setmProgress(150);
        mRadarView.setSearching(true);
        mRadarView.addPoint();
        mRadarView.addPoint();
        mWaveView.setColor(android.R.color.black);

        initData();
        mWaveView.start();
        mRadarViewSelf.start();
        handler.sendEmptyMessage(0);
    }

    @SuppressLint("HandlerLeak")
    private void initData() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mBatteryView.setBatteryPercent(i);
                i += 1;
                handler.sendEmptyMessageDelayed(i, 50);
            }
        };
        /*********ArcScanView动画********/
        ValueAnimator arcScanValueAnimator = ValueAnimator.ofFloat(0, 1);
        arcScanValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        arcScanValueAnimator.setDuration(1000);
        arcScanValueAnimator.setInterpolator(new AccelerateInterpolator());
        arcScanValueAnimator.start();
        arcScanValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mArcScanView.setRatio((Float) animation.getAnimatedValue());
            }
        });
        arcScanValueAnimator.start();
    }
}
