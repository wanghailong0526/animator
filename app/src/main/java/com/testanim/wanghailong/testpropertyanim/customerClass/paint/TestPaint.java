package com.testanim.wanghailong.testpropertyanim.customerClass.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.testanim.wanghailong.testpropertyanim.R;

public class TestPaint extends ImageView {
    private Paint mPaint;
    private Shader mLinearGradent;
    private Shader mBitmapShader;
    private int mRadius;
    private Bitmap bitmap;

    public TestPaint(Context context) {
        super(context);
        init();
    }

    public TestPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestPaint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(20);
//        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mLinearGradent = new LinearGradient(100, 100,
                500, 500,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmapshader);
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = size / 2;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawPoint(50, 50, mPaint);
//        mPaint.setStrokeCap(Paint.Cap.SQUARE);
//        canvas.drawPoint(60, 60, mPaint);
//        mPaint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawPoint(70, 70, mPaint);
//        mPaint.setShader(mLinearGradent);
//        canvas.drawCircle(300, 300, 50, mPaint);
        int scale = (mRadius * 2) / Math.min(bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);

    }
}
