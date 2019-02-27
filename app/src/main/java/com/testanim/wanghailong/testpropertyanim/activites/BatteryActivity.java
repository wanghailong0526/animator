package com.testanim.wanghailong.testpropertyanim.activites;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.testanim.wanghailong.testpropertyanim.R;
import com.testanim.wanghailong.testpropertyanim.customview.BatteryImageView;

import org.json.JSONObject;

/**
 * Created by wanghailong on 2018/7/2.
 * <p>
 * 电池状态页面
 */

public class BatteryActivity extends Activity implements View.OnClickListener {


    private BatteryImageView mIvBatteryStatus;
    private TextView mTvChargingProgress;
    private TextView mTvBatteryStatus;
    private ImageView mIvClose;
    private ImageView mIvBatteryChargingLogo;
    private FrameLayout mFlNativeAd;
    private FrameLayout mFlBannerAd;

    private BatteryBroadCastReceiver mReceiver;

    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, BatteryActivity.class);
            context.startActivity(intent);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        initView();
        initData();
//        showNativeAd(false);
//        showBannerAd(false);
    }

//    @Override
//    protected String getSceneType() {
//        return IOutSceneMgr.VALUE_STRING_CHARGE_SCENE_TYPE;
//    }

    public void onResume() {
        super.onResume();
        mReceiver = new BatteryBroadCastReceiver();
        // 监听电量变化
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);
    }

//    @Override
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        if (intent == null) {
//            return;
//        }
//        initData();
//    }

    private void initData() {
        mIvBatteryStatus.setBatteryPercent(0);
    }

//    private void requestAd() {
//        //充电中的广告
//        UtilsAd.requestAd(getNativeAdConfig(), IOutSceneMgr.VALUE_STRING_CHARGE_SCENE_TYPE, IOutSceneMgr.VALUE_STRING_CHARGE_SCENE_TYPE);
//        UtilsAd.requestAd(getInterstitialAdConfig(), IOutSceneMgr.VALUE_STRING_CHARGE_SCENE_TYPE, IOutSceneMgr.VALUE_STRING_CHARGE_SCENE_TYPE);
//        //充电完成的广告
//        UtilsAd.requestAd(mIAdMgr.getAdConfig(IOutConfig.VALUE_STRING_CONFIG_BANNER_BATTERY_COMPLETE_AD_KEY), IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE, IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE);
//        UtilsAd.requestAd(mIAdMgr.getAdConfig(IOutConfig.VALUE_STRING_CONFIG_NATIVE_BATTERY_COMPLETE_AD_KEY), IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE, IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE);
//        UtilsAd.requestAd(mIAdMgr.getAdConfig(IOutConfig.VALUE_STRING_CONFIG_INTERSTITIAL_BATTERY_COMPLETE_AD_KEY), IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE, IOutSceneMgr.VALUE_STRING_CHARGE_COMPLETE_SCENE_TYPE);
//    }

//    @Override
//    public ViewGroup getNativeAdLayout() {
//        return mFlNativeAd;
//    }
//
//    @Override
//    public ViewGroup getBannerAdLayout() {
//        return mFlBannerAd;
//    }

    private void initView() {
        mIvBatteryStatus = findViewById(R.id.iv_battery_status);
//        mIvBatteryChargingLogo = findViewById(R.id.iv_battery_charging_logo);
//        mTvChargingProgress = findViewById(R.id.tv_charging_progress);
        mTvBatteryStatus = findViewById(R.id.tv_battery_status);
        mIvClose = findViewById(R.id.iv_close);
//        mFlNativeAd = findViewById(R.id.fl_native_ad);
//        mFlBannerAd = findViewById(R.id.fl_banner_ad);
        mIvClose.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        finish();
    }

    public class BatteryBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            String action = intent.getAction();
            /*****处理电池电量*****/
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                // 获取当前电量
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                // 电量的总刻度
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);

                int batteryChargingProgress = (level * 100) / scale;

                String chargingText = batteryChargingProgress + "%";
                mIvBatteryStatus.setBatteryPercent(batteryChargingProgress);
//                mTvChargingProgress.setText(chargingText);

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {//充电
//                    mIvBatteryChargingLogo.setVisibility(View.VISIBLE);
                    mTvBatteryStatus.setText(getResources().getText(R.string.battery_charging));
                } else {//不充电了
//                    mIvBatteryChargingLogo.setVisibility(View.GONE);
                    finish();
                    JSONObject jsonObject = new JSONObject();
//                    UtilsJson.JsonSerialization(jsonObject, "close_button", "charge_complete");
//                    UtilsLog.statisticsLog("charge", "close", jsonObject);
                }
            }
        }
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}