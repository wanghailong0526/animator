package com.testanim.wanghailong.testpropertyanim.customerClass;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

/**
 * Created by wanghailong on 2018/5/27.
 */

public class MyView extends View {
    private Paint mPaint;
    private Point currentPoint;
    public static final float RADIOS = 50f;

    public String getColor() {
        return color;
    }


    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    private String color;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIOS, RADIOS);
            drawCircle(canvas);
            startAnimator();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIOS, mPaint);
    }

    private void startAnimator() {
        Point startPoint = new Point(getWidth()/2, RADIOS);
        Point endPoint = new Point(getWidth()/2, getHeight()-RADIOS);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint = (Point) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });

        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(),
                "#0000FF", "#FF0000");
        AnimatorSet animSet = new AnimatorSet();
//        animSet.setInterpolator(new AccelerateInterpolator());
        animSet.setInterpolator(new BounceInterpolator());
        animSet.play(valueAnimator).with(anim2);
        animSet.setDuration(5000);
        animSet.start();
    }

}
