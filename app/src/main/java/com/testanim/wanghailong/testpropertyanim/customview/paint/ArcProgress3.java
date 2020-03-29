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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * @author : wanghailong
 * @description:
 */
public class ArcProgress3 extends View {
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

    public ArcProgress3(Context context) {
        this(context, null);
    }

    public ArcProgress3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        minWidth = dipTopx(minWidth);
        minHeight = dipTopx(minHeight);
        mStrokeWidthArc = dipTopx(14);
        mStrokeWidthCircle = dipTopx(2);
        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidthArc);
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
        minWidth = 160;
        minHeight = 80;
        minWidth = dipTopx(minWidth);
        minHeight = dipTopx(minHeight);
        minWidth += getPaddingLeft() + getPaddingRight();
        minHeight += getPaddingTop() + getPaddingBottom();

        if (widthMode == MeasureSpec.AT_MOST) {//宽为wrap_content
            if (heightMode == MeasureSpec.EXACTLY) {//高为match_parent或确切值
                width = (height - getPaddingTop() - getPaddingBottom()) * 2 + getPaddingLeft() + getPaddingRight();
            } else {//宽高都是wrap_content,取默认宽高
                width = minWidth;
                height = minHeight;
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {//宽为match_parent或确切值
            if (heightMode == MeasureSpec.EXACTLY) {//高为match_parent或确切值
                //取 宽,(高*2) 的最小值
                width = Math.min(width - getPaddingLeft() - getPaddingRight(), (height - getPaddingTop() - getPaddingBottom()) * 2);
                height = width / 2 + getPaddingTop() + getPaddingBottom();
                width = width + getPaddingLeft() + getPaddingRight();
            } else {
                height = (width - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingTop() + getPaddingBottom();
            }
        } else {
            width = minWidth;
            height = minHeight;
        }
        setMeasuredDimension(width, height + mStrokeWidthArc / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w == 0 || h == 0) {
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = getPaddingLeft() + mStrokeWidthArc / 2.0f;
        mRectF.top = getPaddingTop() + mStrokeWidthArc / 2.0f;
        mRectF.right = getMeasuredWidth() - getPaddingRight() - mStrokeWidthArc / 2.0f;
        mRectF.bottom = getMeasuredWidth() - getPaddingBottom() - mStrokeWidthArc / 2.0f;
        /*****起始角度为270度减去整个圆的一半,顺时针旋转****/
        mStartAngle = (270 - mArcAngle / 2.0f);
        mX = (float) (mRectF.centerX() + (mRectF.width() / 2.0f) * Math.cos(Math.toRadians(mSweepAngle + mStartAngle)));
        mY = (float) (mRectF.centerY() + (mRectF.width() / 2.0f) * Math.sin(Math.toRadians(mSweepAngle + mStartAngle)));
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

//        mPaint.setColor(default_finished_color);
//        canvas.drawLine(mRectF.centerX(), mRectF.centerY(), mX, mY,mPaint);

        canvas.restore();
    }


    /**
     * 开始动画
     */
    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(2, mAvailable);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                mSweepAngle = (mProgress / mMax * mArcAngle) + 1;
                mX = (float) (mRectF.centerX() + (mRectF.width() / 2.0f) * Math.cos(Math.toRadians(mSweepAngle + mStartAngle)));
                mY = (float) (mRectF.centerY() + (mRectF.width() / 2.0f) * Math.sin(Math.toRadians(mSweepAngle + mStartAngle)));
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

