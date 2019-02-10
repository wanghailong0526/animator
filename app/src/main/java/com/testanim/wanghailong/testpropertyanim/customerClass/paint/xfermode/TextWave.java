package com.testanim.wanghailong.testpropertyanim.customerClass.paint.xfermode;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author : wanghailong
 * @description: 水波在文字上动
 * 先画的是dst,后画的是src
 */
public class TextWave extends View {
    Paint mPaint = null;
    Paint mTextPaint = null;
    Path mPath = null;
    Bitmap mSrcBitmap = null;
    Bitmap mDstBitmap = null;
    String text = "aKaic'Blog";
    private float mTextWidth = 0f;
    private float mTextHeight = 0f;
    private float mItemWaveLength = 0f;
    private float dx = 0f;
    private ValueAnimator mAnimator;
    private int mWidth, mHeight;


    public TextWave(Context context) {
        this(context, null);
    }

    public TextWave(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextWave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        //贝塞尔曲线画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //文字画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextSize(130);

        //文字宽度
        mTextWidth = mTextPaint.measureText(text);
        //文字高度
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.descent - fontMetrics.ascent;

        //贝塞尔曲线波长
        mItemWaveLength = mTextWidth;

        //贝塞尔曲线路径
        mPath = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mSrcBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mDstBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Canvas srcCanvas = new Canvas(mSrcBitmap);
        srcCanvas.drawText(text, mWidth / 2.0f - mTextWidth / 2.0f, mTextHeight, mTextPaint);

        //绘制文本 第一遍
        canvas.drawBitmap(mSrcBitmap, 100, 100, mPaint);

        //使用离屏缓存
        int saveCount = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        mPath.reset();
        //这里的贝塞尔曲线的坐标为(文字开始绘制的X坐标，文字基线Y)
        mPath.moveTo(-mItemWaveLength + dx, mTextHeight / 2.0f);

        float mHalfWaveLength = mItemWaveLength / 2.0f;

        for (float i = -mItemWaveLength; i < mTextWidth + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(mHalfWaveLength / 2, 50, mHalfWaveLength, 0);
            mPath.rQuadTo(mHalfWaveLength / 2, -50, mHalfWaveLength, 0);
        }

        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();

        Canvas dstCanvas = new Canvas(mDstBitmap);

        /**擦除 dstCanvas 这个画布上的信息（这个很重要）**/
        dstCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        dstCanvas.drawPath(mPath, mPaint);

        //绘制 目标图像
        canvas.drawBitmap(mDstBitmap, 100, 100, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //绘制 绘制源图像
        canvas.drawBitmap(mSrcBitmap, 100, 100, mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(saveCount);
    }

    public void startAnim() {
        mAnimator = ValueAnimator.ofFloat(0, mItemWaveLength);
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        mAnimator.start();
    }

    public void stopAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
    }
}
