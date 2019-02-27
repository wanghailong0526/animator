package com.testanim.wanghailong.testpropertyanim.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * Created by wanghailong on 2018/7/28.
 * https://www.imooc.com/video/8640
 * 幸运转盘
 */

public class LuckyPan extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Thread mDrawableThread;//serfaceview的绘制线程
    private boolean isRunnable;//是否运行，用于控制线程
    private Canvas mCanvas;//用于绘制
    /*****奖品的名称*****/
    private String[] mStrs = new String[]{"单反相机", "IPAD", "恭喜发财", "IPHONE", "服装一套", "恭喜发财"};

    /*****奖品的图片Id*****/
    private int[] mImgs = new int[]{R.drawable.danfan, R.drawable.ipad, R.drawable.f015, R.drawable.iphone, R.drawable.meizi, R.drawable.f015};
    /*****奖品图片*****/
    private Bitmap[] mBitmaps;

    /*****每个盘块的颜色*****/
    private int[] mItemBgColor = new int[]{0xffffc300, 0xfff17e01, 0xffffc300, 0xfff17e01, 0xffffc300, 0xfff17e01};

    /*****奖项的数量*****/
    private int mItemCout = 6;
    /*****整个盘块的范围*****/
    private RectF mRange;
    /*****整个盘块的直径**需要减去padding***/
    private int mRadius;
    /*****绘制盘块的画笔*****/
    private Paint mArcPaint;

    /*****绘制文本的画笔*****/
    private Paint mTextPaint;

    /*****盘块滚动的速度*****/
    private double mSpeed = 0;

    /*****绘制的起始角度*****/
    private volatile int mStartAngle = 0;

    /*****是否点击了停止按钮**避免用户一直点停止**当点了停止转盘还要转一会儿才停止*/
    private boolean isShouldEnd;
    /*****转盘的中心位置*****/
    private int mCenter;

    /*****本view的padding**取所有padding的最小值*现在以paddingLeft或padding为准**/
    private float mPadding;
    /*****整个view背景***bitmap**/
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
    /*****文字大小**20像素***/
    private float mTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());


    public LuckyPan(Context context) {
        super(context);
        init();
    }

    public LuckyPan(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LuckyPan(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        requestFocusable();
        mHolder = getHolder();
        mHolder.addCallback(this);

    }

    private void requestFocusable() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mPadding = getPaddingLeft();
        mRadius = (int) (width - mPadding * 2);//直径
        mCenter = width / 2;//中心点
        setMeasuredDimension(width, width);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        /*****初始化工作*****/
        //初始化盘块的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mArcPaint.setFilterBitmap(true);
        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        //初始化盘块范围
        mRange = new RectF(mPadding, mPadding, mPadding + mRadius, mPadding + mRadius);
        //初始化奖品图片
        mBitmaps = new Bitmap[mItemCout];
        for (int i = 0; i < mItemCout; i++) {
            mBitmaps[i] = BitmapFactory.decodeResource(getResources(), mImgs[i]);
        }

        /*****当serviceview创建的时候，启动线程*****/
        isRunnable = true;
        mDrawableThread = new Thread(this);
        mDrawableThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        /*****SerfaceViewDestroyed停止绘制*****/
        isRunnable = false;
    }

    @Override
    public void run() {
        /*****若isRunnable为true*****/
        while (isRunnable) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            if (end - start < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*****绘制方法*****/
    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //draw something
                //画背景
                drawBg();
                //画每个盘块 使用循环画每个盘块
                float tempAngle = mStartAngle;//记录角度
                float sweepAngel = 360 / mItemCout;//每个盘块的角度
                for (int i = 0; i < mItemCout; i++) {
                    mArcPaint.setColor(mItemBgColor[i]);
                    //绘制盘块
                    mCanvas.drawArc(mRange, tempAngle, sweepAngel, true, mArcPaint);
                    //绘制文本
                    drawText(tempAngle, sweepAngel, mStrs[i]);
                    //绘制奖品图片
                    drawIcon(tempAngle, mBitmaps[i]);

                    tempAngle += sweepAngel;
                }
                mStartAngle += mSpeed;
                if (isShouldEnd) {
                    mSpeed -= 1;//速度递减，使转盘缓慢停止
                }
                if (mSpeed <= 0) {
                    mSpeed = 0;
                    isShouldEnd = false;
                }
            }

        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    /**
     * 点击启动转盘
     */
    public void luckyPanStart() {
        mSpeed = 50;
        isShouldEnd = false;
    }

    /**
     * 点击停止转盘
     */
    public void luckyPanStop() {
        isShouldEnd = true;
    }

    /**
     * 转盘是否在旋转
     * 当速度不等于0，转盘在旋转
     *
     * @return
     */
    public boolean isStart() {
        return mSpeed != 0;
    }

    /**
     * 停止按钮是否按下
     *
     * @return
     */
    public boolean isShouldEnd() {
        return isShouldEnd;
    }


    /**
     * 绘制奖品对应的图片
     *
     * @param tempAngle
     * @param mBitmap
     */
    private void drawIcon(float tempAngle, Bitmap mBitmap) {
        //设置图片的宽度为直径的8分之一
        int iconWidth = mRadius / 8;
        //度数 * (Math.PI/180:每度的弧度) 得到弧度制的角度值
        float angle = (float) ((tempAngle + 360 / mItemCout / 2) * Math.PI / 180);
        //x,y为图片的圆心坐标
        int x = (int) (mCenter + mRadius / 4 * Math.cos(angle));
        int y = (int) (mCenter + mRadius / 4 * Math.sin(angle));
        //确定图片的位置,使用矩形去约束图片的位置
        Rect imageRectF = new Rect(x - iconWidth / 2, y - iconWidth / 2, x + iconWidth / 2, y + iconWidth / 2);
        mCanvas.drawBitmap(mBitmap, null, imageRectF, null);
    }

    /**
     * 绘制文本
     * 文本有弧度，需要path
     *
     * @param tempAngle
     * @param sweepAngel
     * @param mStr
     */
    private void drawText(float tempAngle, float sweepAngel, String mStr) {
        Path textPath = new Path();
        textPath.addArc(mRange, tempAngle, sweepAngel);
        //利用水平偏移量让文字居中
        int textWidth = (int) mTextPaint.measureText(mStr);
        //直径* Math.PI=周长  周长/盘块数量/2=每块的弧度长度的一半，再减去文字长度的一半，得到文字绘制的起始位置
        int hOffset = (int) (mRadius * Math.PI / mItemCout / 2 - textWidth / 2);
        int vOffset = mRadius / 2 / 6;
        mCanvas.drawTextOnPath(mStr, textPath, hOffset, vOffset, mTextPaint);

    }

    /**
     * 画背景
     */
    private void drawBg() {
        mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        mCanvas.drawColor(0xffffffff);
        mCanvas.drawBitmap(mBgBitmap, null, new RectF(mPadding / 2, mPadding / 2, getMeasuredWidth() - mPadding / 2, getMeasuredWidth() - mPadding / 2), null);
    }
}
