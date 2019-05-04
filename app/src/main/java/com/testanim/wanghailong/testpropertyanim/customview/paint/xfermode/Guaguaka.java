package com.testanim.wanghailong.testpropertyanim.customview.paint.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.testanim.wanghailong.testpropertyanim.R;

import java.nio.ByteBuffer;

/**
 * @author : wanghailong
 * @description: 刮刮卡
 * 原理是使用 SRC_OUT 模式，
 * 当 目标图像 和 源图像 均不为 空白像素时，相交局域会变成空白像素（完全透明），
 * 所以在绘制路径时，使用的画笔颜色不能采用 Color.TRANSPARENT，必须要有颜色值
 * <p>
 * 先画的为dst,后画的为src
 */
public class Guaguaka extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private Bitmap srcBitmap;
    private Bitmap dstBitmap;
    private Bitmap mBitmap;
    private Path mPath;
    private float eventX;
    private float eventY;
    private float mTextWidth;
    private int mWidth;
    private int mHeight;
    private float mTextHeight;
    private String text = "未中奖";
    private Canvas mTextCanvas;
    private Canvas mDstPathCanvas;
    private boolean mHasMoved = false;
    private boolean mClear = false;
    private volatile boolean isComplete = false;
    private int mTextX;
    private int mTextY;
    private Paint.FontMetrics mFontMetrics;
    private PorterDuffXfermode mPorterDuffXfermodeClear;
    private PorterDuffXfermode mPorterDuffXfermodeSrcout;
    public static final String TAG = "Guaguaka";


    public Guaguaka(Context context) {
        this(context, null);
    }

    public Guaguaka(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Guaguaka(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        System.out.println(TAG + ":init");
        mPorterDuffXfermodeClear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mPorterDuffXfermodeSrcout = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        //绘制path,目标图像,源图像的画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔颜色（不能为完全透明）
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);


        Drawable srcDrawable = getResources().getDrawable(R.drawable.foreground);
        //源图像
        srcBitmap = getBitmap(srcDrawable);
        //目标图像
        dstBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //中奖信息
        mBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //路径（贝塞尔曲线）
        mPath = new Path();
        //绘制中奖信息文字的画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setDither(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextSize(30);

        //获取文字宽度
        mTextWidth = mTextPaint.measureText(text);
        //获取文字高度
        mFontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = mFontMetrics.descent - mFontMetrics.ascent;


        //控件的宽高
        mWidth = srcBitmap.getWidth();
        mHeight = srcBitmap.getHeight();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println(TAG + ":onMeasure");
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println(TAG + ":onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mTextCanvas = new Canvas(mBitmap);
        mTextX = (int) ((mWidth - mTextWidth) / 2);
        mTextY = (int) (mHeight / 2 - (mFontMetrics.bottom - mFontMetrics.top) / 2 - mFontMetrics.top);

        //居中绘制文字,也可以直接在onDraw()方法里画
        mTextCanvas.drawText(text, mTextX, mTextY, mTextPaint);

        mDstPathCanvas = new Canvas(dstBitmap);
        //加上这句直接显示了奖项
//        mDstPathCanvas.drawColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println(TAG + ":onDraw");
        /**
         * 禁用硬件加速
         * 如果没有刮开:设置 setLayerType(View.LAYER_TYPE_SOFTWARE, null);
         * 否则:设置 setLayerType(View.LAYER_TYPE_NONE, null);
         * 因为设置了View.LAYER_TYPE_SOFTWARE，view一直刷新，onDraw()方法循环执行
         * 为了避免一直刷新，刮开了后，设置为View.LAYER_TYPE_NONE
         */
        if (!mClear) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        } else {
            setLayerType(View.LAYER_TYPE_NONE, null);
        }

        //画背景的文字
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        if (!mClear) {
            //使用离屏绘制
            int layerID = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint, Canvas.ALL_SAVE_FLAG);

            //绘制 目标图像
            canvas.drawBitmap(dstBitmap, 0, 0, mPaint);
            if (isComplete) {
                //设置 模式 为 clear 用于开奖
                mPaint.setXfermode(mPorterDuffXfermodeClear);
                mClear = true;
            } else {
                //设置 模式 为 SRC_OUT
                mPaint.setXfermode(mPorterDuffXfermodeSrcout);
                mClear = false;
            }

            //绘制源图像
            canvas.drawBitmap(srcBitmap, 0, 0, mPaint);

            mPaint.setXfermode(null);
            canvas.restoreToCount(layerID);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventX = event.getX();
                eventY = event.getY();
                if (!isComplete) {
                    mPath.moveTo(eventX, eventY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*****两点的中点是控制点*****/
                int dx = (int) Math.abs(event.getX() - eventX);
                int dy = (int) Math.abs(event.getY() - eventY);
                if ((dx > 3 || dy > 3) && !isComplete) {
                    mHasMoved = true;
                }
                float endX = (event.getX() - eventX) / 2 + eventX;
                float endY = (event.getY() - eventY) / 2 + eventY;
                mPath.quadTo(eventX, eventY, endX, endY);
                eventX = event.getX();
                eventY = event.getY();
                drawPath();
                break;

            case MotionEvent.ACTION_UP:
                if (!isComplete && mHasMoved) {
                    new Thread(mRunnable).start();
                    mHasMoved = false;
                }
                break;
        }
        invalidate();
        return true;
    }

    private void drawPath() {
        mPath.lineTo(eventX, eventY);
        //先将路径绘制到 dstBitmap上
        mDstPathCanvas.drawPath(mPath, mPaint);
        mPath.reset();
        mPath.moveTo(eventX, eventY);
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


    /**
     * 统计擦除区域任务
     */
    private Runnable mRunnable = new Runnable() {
//        private int[] mPixels;

        @Override
        public void run() {
            float wipeArea = 0;

            Bitmap bitmap = dstBitmap;
//            int w = bitmap.getWidth();
//            int h = bitmap.getHeight();

//            float totalArea = w * h;
//            float totalArea = mTextWidth * mTextHeight;

            /**
             * 文字显示的区域
             */
            float totalArea = ((mTextWidth + mTextX / 2) - (mTextX + mTextX / 2)) * (mTextY - (mTextY - mTextHeight / 2));

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

            //这个是有用的，先注释
//            for (int i = 0; i < w; i++) {
//                for (int j = 0; j < h; j++) {
//                    int pixel = bitmap.getPixel(i, j);
//                    if (pixel == 0) {
//                        wipeArea++;
//                    }
//                }
//            }
            /**
             * 只计算文字显示的区域是否被刮开了,提高效率
             */
            for (int i = (mTextX + mTextX / 2); i < (mTextWidth + mTextX / 2); i++) {
                for (int j = (int) (mTextY - mTextHeight / 2); j < mTextY; j++) {
                    int pixel = bitmap.getPixel(i, j);
                    if (pixel != 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，计算是否刮开
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea / totalArea);
//                Log.e("TAG", percent + "");

                if ((percent) > 0.6) {
                    isComplete = true;
                    postInvalidate();
                }
            }
            //这是另外一种方法计算是否刮开
//            Bitmap bitmap = Bitmap.createBitmap(dstBitmap);
//            float transparentPixelPercent = getTransparentPixelPercent(bitmap);
//            float percent = transparentPixelPercent * 100;
//            if (percent > 0.6) {
//                isComplete = true;
//                postInvalidate();
//            }
        }

    };

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


}
