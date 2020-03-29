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
import com.testanim.wanghailong.testpropertyanim.customview.BatteryImageView2;
import com.testanim.wanghailong.testpropertyanim.customview.WaveView;
import com.testanim.wanghailong.testpropertyanim.customview.paint.AmountView;
import com.testanim.wanghailong.testpropertyanim.customview.paint.ArcProgress;
import com.testanim.wanghailong.testpropertyanim.customview.paint.ArcProgress2;
import com.testanim.wanghailong.testpropertyanim.customview.paint.ArcProgress3;
import com.testanim.wanghailong.testpropertyanim.customview.paint.ArcScanView;
import com.testanim.wanghailong.testpropertyanim.customview.paint.RadarView;
import com.testanim.wanghailong.testpropertyanim.customview.paint.RadarView1;
import com.testanim.wanghailong.testpropertyanim.customview.paint.xfermode.ScanView;
import com.testanim.wanghailong.testpropertyanim.customview.paint.xfermode.TextWave;

public class PaintActivity extends AppCompatActivity {
    private int i = 0;
    private @SuppressLint("HandlerLeak")
    Handler handler;
    private BatteryImageView2 mBatteryView;
    private WaveView mWaveView;
    private ArcScanView mArcScanView;
    private ScanView mScanView;
    private TextWave mTextWave;
    private ArcProgress2 mArcProgress2;
    private ArcProgress3 mArcProgress3;

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, PaintActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pait);

        final AmountView amountView = (AmountView) findViewById(R.id.amount_view);
        final AmountView originalAmountView = (AmountView) findViewById(R.id.original_amountview);
        mArcProgress2 = findViewById(R.id.arc_progress2);
        mArcProgress3 = findViewById(R.id.arc_progress3);
        mArcScanView = findViewById(R.id.arc_scanview);
        mBatteryView = findViewById(R.id.battery_view);
        mWaveView = findViewById(R.id.waveView);
        mScanView = findViewById(R.id.scan_view);
        mTextWave = findViewById(R.id.text_wave);
        RadarView1 mRadarView = findViewById(R.id.randerViewPoint);
        RadarView mRadarViewSelf = findViewById(R.id.radarView);
        ArcProgress mArcProgress = findViewById(R.id.arcprogress);
        mArcProgress.setmProgress(150);
        mRadarView.setSearching(true);
        mRadarView.addPoint();
        mRadarView.addPoint();
        mWaveView.setColor(android.R.color.black);

        initData();

        /***********动画开始************/
        mWaveView.start();
        mRadarViewSelf.start();
        mTextWave.startAnim();


        handler.sendEmptyMessage(0);

        initAnimation();


        //延时4秒执行动画
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mArcProgress2.setData(10000, 7800);
                mArcProgress3.setData(10000, 7800);
                amountView.start();
                originalAmountView.start();
            }
        }, 3000);

    }

    /**
     * 初始化ScanVeiw
     */
    private void initAnimation() {
        mScanView.setSnowDrawable(R.drawable.out_icon_phone_out);
        mScanView.setDstDrawable(R.drawable.out_icon_phone_inner);
        mScanView.setScanLineDrawable(R.drawable.icon_scan_line, R.drawable.icon_scan_line_up);
        mScanView.setDuration(5000);
        mScanView.start();
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

    @Override
    protected void onResume() {
        super.onResume();
        mScanView.start();
        mWaveView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanView.stop();
        mWaveView.stop();
        mTextWave.stopAnim();
    }
}
