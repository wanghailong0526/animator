package com.testanim.wanghailong.testpropertyanim.customerClass.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @author : wanghailong
 * @description:
 */
public class FontView extends View {
    private Paint mTextPaint;
    private String mText = "********  4465";
    private int mTextWidth = 0;
    private float baseX = 0f;
    private float baseY = 0f;

    public FontView(Context context) {
        this(context, null);
    }

    public FontView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.black));
        mTextPaint.setTextSize(50);
//        mTextWidth = (int) mTextPaint.measureText(mText);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
//
//        baseX = canvas.getWidth() >> 1 - (int)(mTextPaint.measureText(mText)) >> 1;
//        baseY = ((canvas.getHeight() >> 1) - (int) ((mTextPaint.descent() + mTextPaint.ascent())) >> 1);
//        canvas.drawText(mText, baseX, baseY, mTextPaint);

        //文字的x轴坐标
        float stringWidth = mTextPaint.measureText(mText);
        float x = (getWidth() - stringWidth) / 2;
        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText(mText, x, y, mTextPaint);



        canvas.restore();
    }
}
