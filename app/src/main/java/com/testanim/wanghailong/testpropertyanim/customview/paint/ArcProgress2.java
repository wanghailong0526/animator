package com.testanim.wanghailong.testpropertyanim.customview.paint;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @author : wanghailong
 * @description:
 */
public class ArcProgress2 extends View {
    private int mStrokeWidthArc = 0;//arc的宽
    private int mStrokeWidthCircle = 0;//小圆的宽
    private final int default_unfinished_color = Color.parseColor("#FFD7E1");
    private final int default_finished_color = Color.parseColor("#FF4070");
    private final int head_circle_color = Color.WHITE;

    private Paint mPaint = null;
    private RectF mRectF = null;
    private float mMax = 0;//最大额度
    private float mAvailable = 0;//可用额度
    /******此时一个圆的圆周为360乘以0.5*****/
    private float mArcAngle = 360 * 0.5f;
    private float mStartAngle = 180.f;//开始角度
    private float mSweepAngle = 1.0f;//旋转角度，旋转多少度
    private int mSize = 0;
    private int minWidth = 160;//默认宽
    private int minHeight = 80;//默认高
    private float mProgress = .0f;
    private float mX;
    private float mY;

    public void setData(float max, float available) {
        this.mMax = max;
        this.mAvailable = available;
        startAnimator();
    }

    public float getmProgress() {
        return mProgress;
    }

    public ArcProgress2(Context context) {
        this(context, null);
    }

    public ArcProgress2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStrokeWidthArc = dipTopx(14);
        mStrokeWidthCircle = dipTopx(2);
        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidthArc);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mRectF = new RectF();
        minWidth = dipTopx(minWidth);
        minHeight = dipTopx(minHeight);
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
        setMeasuredDimension(mSize, mSize / 2 + mStrokeWidthArc / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w == 0 || h == 0) {
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = mStrokeWidthArc / 2.0f;
        mRectF.top = mStrokeWidthArc / 2.0f;
        mRectF.right = mSize - mStrokeWidthArc / 2.0f;
        mRectF.bottom = mSize - mStrokeWidthArc / 2.0f;
        /*****起始角度为270度减去整个圆的一半,顺时针旋转****/
        mStartAngle = (270 - mArcAngle / 2);
        mX = (float) (mRectF.centerX() + (mRectF.width() / 2) * Math.cos(Math.toRadians(mSweepAngle + mStartAngle)));
        mY = (float) (mRectF.centerX() + (mRectF.width() / 2) * Math.sin(Math.toRadians(mSweepAngle + mStartAngle)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        mPaint.setColor(default_unfinished_color);
        mPaint.setStrokeWidth(mStrokeWidthArc);
        canvas.drawArc(mRectF, mStartAngle, mArcAngle, false, mPaint);


        mPaint.setColor(default_finished_color);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaint);


        mPaint.setColor(head_circle_color);
        mPaint.setStrokeWidth(mStrokeWidthCircle);
        canvas.drawCircle(mX, mY, 2, mPaint);

        canvas.restore();
    }


    /**
     * 开始动画
     */
    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(2, mAvailable);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                mSweepAngle = (mProgress / mMax * mArcAngle) + 1;
                mX = (float) (mRectF.centerX() + (mRectF.width() / 2) * Math.cos(Math.toRadians(mSweepAngle + mStartAngle)));
                mY = (float) (mRectF.centerX() + (mRectF.width() / 2) * Math.sin(Math.toRadians(mSweepAngle + mStartAngle)));
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    private int dipTopx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private int pxTodp(int pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getResources().getDisplayMetrics());
    }
}
