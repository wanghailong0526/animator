package com.testanim.wanghailong.testpropertyanim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.testanim.wanghailong.testcoordinatorlayout.TestcollapsingToolbarLayoutActivity;
import com.testanim.wanghailong.testcoordinatorlayout.testNestedScrollView.NestedScrollViewActivity;
import com.testanim.wanghailong.testpropertyanim.activites.BatteryActivity;
import com.testanim.wanghailong.testpropertyanim.activites.LuckyPanActivity;
import com.testanim.wanghailong.testpropertyanim.activites.MyEditTextActivity;
import com.testanim.wanghailong.testpropertyanim.activites.NoScrollActivity;
import com.testanim.wanghailong.testpropertyanim.activites.PaintActivity;
import com.testanim.wanghailong.testpropertyanim.activites.TestConstraintLayoutActivity;
import com.testanim.wanghailong.testpropertyanim.activites.TestScrollViewGroupActivity;
import com.testanim.wanghailong.testpropertyanim.activites.TestScrollerActivity;
import com.testanim.wanghailong.testpropertyanim.customview.DynamicHeartView;
import com.testanim.wanghailong.testpropertyanim.customview.RadarView;
import com.testanim.wanghailong.testpropertyanim.scroll.CoordinatorLayoutActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mLuckyPan;
    private TextView mTestPaint;
    private TextView mBattery;
    private TextView mNoscrollView;
    private TextView mTextConstraintLayout;
    private TextView mCoordinatorLayout;
    private TextView mTestScroller;
    private TextView mTestScrollViewGroup;
    private TextView mTestNestedScrollView;
    private TextView mTestCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLuckyPan = findViewById(R.id.tv_lucky_pan);
        mTestPaint = findViewById(R.id.TestPaint);
        mBattery = findViewById(R.id.battery_view);
        mNoscrollView = findViewById(R.id.no_scroll_view);
        mTextConstraintLayout = findViewById(R.id.test_constraintLayout);
        mCoordinatorLayout = findViewById(R.id.test_coordinatorLayout);
        mTestScroller = findViewById(R.id.test_scroller);
        mTestScrollViewGroup = findViewById(R.id.test_scroll_view_group);
        mTestNestedScrollView = findViewById(R.id.test_nested_scroll_view);
        mTestCollapsingToolbarLayout = findViewById(R.id.test_collapsing_toolbar_layout);


        DynamicHeartView mDynamicHeartView = findViewById(R.id.view_heart);
        mDynamicHeartView.startPathAnim(2000);
        RadarView viewById = findViewById(R.id.radarView);
//        viewById.start();
        mLuckyPan.setOnClickListener(this);
        mTestPaint.setOnClickListener(this);
        mBattery.setOnClickListener(this);
        mNoscrollView.setOnClickListener(this);
        mTextConstraintLayout.setOnClickListener(this);
        mCoordinatorLayout.setOnClickListener(this);
        mTestScroller.setOnClickListener(this);
        mTestScrollViewGroup.setOnClickListener(this);
        mTestNestedScrollView.setOnClickListener(this);
        mTestCollapsingToolbarLayout.setOnClickListener(this);

        findViewById(R.id.my_edit_text).setOnClickListener(this);
//        TextView tv = findViewById(R.id.tv);
//        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(tv, "alpha", 1, 0, 1);
//        animatorAlpha.setDuration(5000);
//        animatorAlpha.start();

//        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(tv, "rotation", 0, 360);
//        animatorRotation.setDuration(5000);
//        animatorRotation.start();

//        float translationX = tv.getTranslationX();
//        ObjectAnimator animatorTranslationX = ObjectAnimator.ofFloat(tv, "translationX", -500f, 0);
//        animatorTranslationX.setDuration(2000);
//        animatorTranslationX.start();

//        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(tv, "scaleY", 1, 3, 1);
//        animatorScaleY.setDuration(2000);
//        animatorScaleY.start();
//        ObjectAnimator moveIn = ObjectAnimator.ofFloat(tv, "translationX", -500f, 0f);
//        ObjectAnimator rotate = ObjectAnimator.ofFloat(tv, "rotation", 0f, 360f);
//        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f, 1f);
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(rotate).with(fadeInOut).after(moveIn);
//        animSet.setDuration(5000);
//        animSet.start();
//        AnimatorSet set = new AnimatorSet();
//        /*****将传入动画，加入到已有动画之前*****/
//        set.play(animatorRotation).with(animatorAlpha).with(animatorScaleY).after(2000).after(animatorTranslationX);
//        set.setDuration(1000);
//        set.start();
//        String deviceID = "";
//        deviceID = getDeviceID(this);
//        System.out.println("deviceID:" + deviceID);
//        int a = 0;
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        cachedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), "aa", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        });
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
//        fixedThreadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), "bb", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        });
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
//        scheduledExecutorService.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//        scheduledExecutorService.schedule(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), "bb", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }, 5, TimeUnit.SECONDS);
//        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), "cc", Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//        }, 2, 3, TimeUnit.SECONDS);
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lucky_pan:
                LuckyPanActivity.start(this);
                break;
            case R.id.TestPaint:
                PaintActivity.start(this);
                break;
            case R.id.battery_view:
                BatteryActivity.start(this);
                break;
            case R.id.my_edit_text:
                MyEditTextActivity.start(this);
                break;
            case R.id.no_scroll_view:
                NoScrollActivity.start(this);
                break;
            case R.id.test_constraintLayout:
                TestConstraintLayoutActivity.start(this);
                break;
            case R.id.test_coordinatorLayout:
                CoordinatorLayoutActivity.start(this);
                break;
            case R.id.test_scroller:
                TestScrollerActivity.start(this);
                break;
            case R.id.test_scroll_view_group:
                TestScrollViewGroupActivity.start(this);
                break;
            case R.id.test_nested_scroll_view:
                NestedScrollViewActivity.start(this);
                break;
                case R.id.test_collapsing_toolbar_layout:
                TestcollapsingToolbarLayoutActivity.start(this);
                break;
        }
    }

    /**
     * 获取设备ID
     * 需添加权限 {@code <uses-permission android:name="android.permission.READ_PHONE_STATE"/>}
     *
     * @param context
     * @return
     */
//    @SuppressLint("MissingPermission")
//    public static String getDeviceID(Context context) {
//        String deviceId = "";
//        try {
//            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (manager != null) deviceId = manager.getDeviceId();
//        } catch (Exception var4) {
//            var4.printStackTrace();
//        }
//        return deviceId;
//    }

}
