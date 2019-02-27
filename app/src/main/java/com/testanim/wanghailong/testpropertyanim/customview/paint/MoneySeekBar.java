package com.testanim.wanghailong.testpropertyanim.customview.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;

/**
 * @author : wanghailong
 * @description:
 */
public class MoneySeekBar extends AppCompatSeekBar {
    private Paint mPaint;
    private int mPaddingTop;
    private String mTextStart = "1000";
    private String mTextEnd = "2000";
    private float mTextWidth;
    private float mTextHeight;

    public MoneySeekBar(Context context) {
        this(context, null);
    }

    public MoneySeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoneySeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(15);
        mTextWidth = mPaint.measureText(mTextStart);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        mTextHeight = fontMetrics.descent - fontMetrics.ascent;


    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect progressBounds = getProgressDrawable().getBounds();
        Rect thumbBounds = getThumb().getBounds();
        canvas.drawText(mTextStart, getLeft() + mTextWidth / 2, progressBounds.height() + mTextHeight * 0.1f, mPaint);
        canvas.drawText(mTextStart, getRight() + mTextWidth / 2, progressBounds.height() + mTextHeight, mPaint);
    }
}
