package com.testanim.wanghailong.testpropertyanim.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @author : wanghailong
 * @date : $data$ $time$
 * @description:
 */
public class BatteryImageView extends AppCompatImageView {
    private int mPercent = -1;//当前充电电量比例
    private Paint mPaint;
    private int mWidth;//控件宽度
    private int mHeight;//控件高度


    public BatteryImageView(Context context) {
        super(context);
        init();
    }

    public BatteryImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BatteryImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.battery_charging));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPercent < 0) return;
        
        /***绘制充电电量***/
        int maxHeight = getHeight();
        drawBatteryBody(canvas, maxHeight);

        super.onDraw(canvas);
    }

    /****绘制充电电量*****/
    private void drawBatteryBody(Canvas canvas, int maxHeight) {
        int percentHeight = mPercent * maxHeight / 100;
        RectF rectBatteryCharging = new RectF(0, getHeight() - percentHeight, getWidth(), getHeight());
        canvas.drawRoundRect(rectBatteryCharging, 10, 10, mPaint);
    }

    public int getUpPercent() {
        return mPercent;
    }

    public void setBatteryPercent(int percent) {
        this.mPercent = percent;
        invalidate();
    }
}
