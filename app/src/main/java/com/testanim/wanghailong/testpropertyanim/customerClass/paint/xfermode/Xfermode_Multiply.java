package com.testanim.wanghailong.testpropertyanim.customerClass.paint.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @author : wanghailong
 * @description:
 */
public class Xfermode_Multiply extends View {
    private Paint mPaint;

    public Xfermode_Multiply(Context context) {
        this(context, null);
    }

    public Xfermode_Multiply(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Xfermode_Multiply(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Bitmap bitmapDst = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_dst, null);
        Bitmap bitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.twitter_src, null);

        canvas.drawBitmap(bitmapDst, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(bitmapSrc, 0, 0, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);

    }
}
