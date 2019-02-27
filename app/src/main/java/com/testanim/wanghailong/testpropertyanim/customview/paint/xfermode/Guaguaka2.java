package com.testanim.wanghailong.testpropertyanim.customview.paint.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.testanim.wanghailong.testpropertyanim.R;

import java.nio.ByteBuffer;

/**
 * @author : wanghailong
 * @description: 刮刮卡
 * 原理就是使用 SRC_OUT 模式，
 * 当 目标图像 和 源图像 均不为 空白像素时，相交局域会变成空白像素（完全透明），
 * 所以在绘制途径时，使用的画笔颜色不能采用 Color.TRANSPARENT，必须要有颜色值
 */
public class Guaguaka2 extends View {
    private String mText = "未中奖";//显示文本
    private Paint mTextPaint;//绘制文本的画笔
    private Rect mTextBounds;//文本的边界
    private Drawable mForegroundDrawable;

    /**
     * 画板变量
     */
    private Canvas mCanvas;//缓冲画板
    private Bitmap mLayerBimtap;//图层图片
    private Bitmap mBitmap;//画板上的画纸
    private Paint mPaint;//画笔

    /**
     * 路径变量
     */
    private Path mPath;
    private Paint mPathPaint;
    private float eventX;//记录上一次X坐标
    private float eventY;//记录上一次Y坐标

    private int mWidth, mHeight;


    public Guaguaka2(Context context) {
        this(context, null);
    }

    public Guaguaka2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Guaguaka2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        mTextBounds = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(50);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);//获取到绘制文本的长宽
        mForegroundDrawable = getResources().getDrawable(R.drawable.foreground);
//        mBitmap = getBitmap(mForegroundDrawable);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.parseColor("#c0c0c0"));
        mLayerBimtap = BitmapFactory.decodeResource(getResources(), R.drawable.foreground);
        mWidth = mLayerBimtap.getWidth();
        mHeight = mLayerBimtap.getHeight();
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);//创建一个空白的位图
        mCanvas = new Canvas(mBitmap);//在此Bitmap上作画
        //mCanvas.drawColor(Color.parseColor("#c0c0c0"));//绘制背景颜色
//        mCanvas.drawRoundRect(new RectF(0, 0, width, height), 20, 20, mPaint);//绘制圆角矩形
        mCanvas.drawBitmap(mLayerBimtap, null, new RectF(0, 0, mWidth, mHeight), null);//绘制图层图片


        mPath = new Path();
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPathPaint.setStrokeWidth(20);
        mPathPaint.setColor(Color.RED);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //绘制文本
        canvas.drawText(mText, mWidth / 2 - mTextBounds.width() / 2, mHeight / 2 + mTextBounds.height() / 2, mTextPaint);


        if (!isComplete) {
            //绘制路径
            drawPath();
            //绘制图片【图层背景颜色】
            //canvas.drawBitmap(mBitmap,0,0,null);
            //绘制图片【图层图片】
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    //返回给系统的canvas是一幅带有路径的位图
    private void drawPath() {
//        mPath.lineTo(mLastX, mLastY);
        //这之前是设置了图层图片
        mPathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mPathPaint);
//        mPath.reset();
//        mPath.moveTo(eventX, eventY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventX = event.getX();
                eventY = event.getY();
                mPath.moveTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_MOVE:
                /*****两点的中点是控制点*****/
                float endX = (event.getX() - eventX) / 2 + eventX;
                float endY = (event.getY() - eventY) / 2 + eventY;
                mPath.quadTo(eventX, eventY, endX, endY);
                eventX = event.getX();
                eventY = event.getY();
//                drawPath();
                break;

            case MotionEvent.ACTION_UP:
                new Thread(mRunnable).start();
                break;
        }
        invalidate();
        return true;
    }

    private Bitmap getBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(c);
        return bitmap;
    }

    private volatile boolean isComplete = false;

    public float getTransparentPixelPercent(Bitmap bitmap) {

        if (bitmap == null) {
            return 0f;
        }

        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getHeight() * bitmap.getRowBytes());
        bitmap.copyPixelsToBuffer(buffer);

        byte[] array = buffer.array();

        int len = array.length;
        int count = 0;

        for (int i = 0; i < len; i++) {
            if (array[i] == 0) {
                count++;
            }
        }

        return ((float) (count)) / len;
    }

    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {


            float wipeArea = 0;

            Bitmap bitmap = mBitmap;
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            float totalArea = w * h;

//            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
//            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
//            for (int i = 0; i < w; i++) {
//                for (int j = 0; j < h; j++) {
//                    int index = i + j * w;
//                    if (mPixels[index] == 0) {
//                        wipeArea++;
//                    }
//                }
//            }
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int pixel = bitmap.getPixel(i, j);
                    System.out.println("whl*[" + i + "," + j + "]=" + pixel);
                    if (pixel == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e("TAG", percent + "");

                if (percent > 50) {
                    isComplete = true;
                    postInvalidate();
                }
            }
//            Bitmap bitmap = Bitmap.createBitmap(dstBitmap);
//            float transparentPixelPercent = getTransparentPixelPercent(bitmap);
//            float percent = transparentPixelPercent * 100;
//            if (percent > 0.6) {
//                isComplete = true;
//                postInvalidate();
//            }
        }

    };

}
