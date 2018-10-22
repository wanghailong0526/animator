package com.testanim.wanghailong.testpropertyanim.customerClass;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wanghailong on 2018/7/18.
 */

public class ClearView extends View {
    private Paint paint;
    private PathMeasure pathMeasure;
    private float mViewWidth;
    private float mViewHeight;
    private long duration = 5000;
    private ValueAnimator valueAnimator;

    private float distance;//当前动画执行的百分比取值为0-1
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;
    private RectF mRectF;
    private Paint mPaint2;
    private float startAngle = 360;
    private Paint mPaint3;


    public ClearView(Context context) {
        super(context);
        init();
    }

    public ClearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mRectF = new RectF((float) (mViewWidth * 0.1), (float) (mViewWidth * 0.1),
                (float) (mViewWidth * 0.9), (float) (mViewWidth * 0.9));
        // 绘制渐变效果
        LinearGradient gradient = new LinearGradient((float) (mViewWidth * 0.3),
                (float) (mViewWidth * 0.9), (float) (mViewWidth * 0.1),
                (float) (mViewWidth * 0.5), new int[]{
                Color.parseColor("#458EFD"), Color.GREEN,
                Color.parseColor("#458EFD"), Color.WHITE,
                Color.parseColor("#458EFD")}, null,
                Shader.TileMode.CLAMP);
        mPaint2.setShader(gradient);
    }

    private void init() {
        initPaint();
        initAnimatorListener();
        initAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasArc(canvas);
        canvasArc2(canvas);

    }

    // 绘制旋转的扇形
    private void canvasArc(Canvas canvas) {
        canvas.drawArc(mRectF, startAngle, 60, true, mPaint2);

    }

    private void initAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 360).setDuration(duration);

        valueAnimator.addUpdateListener(animatorUpdateListener);

        valueAnimator.addListener(animatorListener);
        valueAnimator.start();
    }

    private void initAnimatorListener() {
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                distance = (float) animation.getAnimatedValue();
                startAngle--;
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initPaint() {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        paint.setShadowLayer(15, 0, 0, Color.WHITE);//白色光影效果

        mPaint2 = new Paint();
        mPaint2.setStrokeWidth(5);
//        mPaint2.setColor(Color.parseColor("#00ff00"));

        mPaint3 = new Paint();
        mPaint3.setStrokeWidth(3);
        mPaint3.setAntiAlias(true);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.parseColor("#ffffff"));


    }

    // 绘制旋转的扇形
    private void canvasArc2(Canvas canvas) {
        canvas.drawArc(mRectF, startAngle, 1, true, mPaint3);
    }
}
