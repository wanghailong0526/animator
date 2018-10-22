package com.testanim.wanghailong.testpropertyanim.customerClass.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author : wanghailong
 * @description:
 */
public class ArcProgress extends View {
    private int mStrokeWidth = 0;
    private final int default_unfinished_color = Color.rgb(72, 106, 176);
    private final int default_finished_color = Color.WHITE;
    private Paint mPaint = null;
    private RectF mRectF = null;
    private int mMax = 100;
    /******此时一个圆的圆周为360乘以0.8*****/
    private float mArcAngle = 360 * 0.8f;
    private float mStartAngle = 0.0f;//开始角度
    private float mSweepAngle = 0.0f;//旋转角度，旋转多少度
    private int mSize = 0;

    private float mProgress = .0f;

    public void setmProgress(float mProgress) {
        if (mProgress > 100) {
            mProgress = 100;
        }
        this.mProgress = mProgress;
        postInvalidate();
    }

    public float getmProgress() {
        return mProgress;
    }

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidth = dipTopx(8);
        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        mSize = Math.min(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = mStrokeWidth / 2;
        mRectF.top = mStrokeWidth / 2;
        mRectF.right = mSize - mStrokeWidth / 2;
        mRectF.bottom = mSize - mStrokeWidth / 2;
        /*****起始角度为270度减去整个圆的一半,顺时针旋转****/
        mStartAngle = (270 - mArcAngle / 2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        mPaint.setColor(default_unfinished_color);
        canvas.drawArc(mRectF, mStartAngle, mArcAngle, false, mPaint);
        mSweepAngle = (mProgress / mMax * mArcAngle);
        mPaint.setColor(default_finished_color);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);

        canvas.restore();
    }


    private int dipTopx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private int pxTodp(int pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getResources().getDisplayMetrics());
    }
}
