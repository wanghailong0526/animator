package com.testanim.wanghailong.testpropertyanim.customerClass;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by wanghailong on 2018/7/18.
 * 病毒扫描控件
 */

public class VirusScan extends View {
    private Path mBigCirclePath;
    private Path mLittleCirclePath;
    private Paint mCirclePaint;
    private Paint mLinePaint;
    private int mWidth;
    private int mHeigit;
    private PathMeasure mPathMeasure;
    private float pos_left[];
    private float pos_right[];
    private float pos_top[];
    private float pos_bottom[];
    private Shader mShader;
    private Paint mShaderPaint;
    private Matrix matrix;
    //旋转效果起始角度
    private int start = 0;

    private float length;

    public boolean isstart = false;
    private VirusScan.ScanThread mThread;
    //设定雷达扫描方向
    private int direction = DEFAULT_DIERCTION;

    public final static int CLOCK_WISE = 1;

    public final static int ANTI_CLOCK_WISE = -1;
    private boolean threadRunning = true;
    public static final float STROKE_WIDTH = 2f;//圆的strokewidth


    @IntDef({CLOCK_WISE, ANTI_CLOCK_WISE})
    public @interface RADAR_DIRECTION {

    }

    //默认为顺时针
    private final static int DEFAULT_DIERCTION = CLOCK_WISE;

    public VirusScan(Context context) {
        super(context);
        init();
    }

    public VirusScan(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VirusScan(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeigit = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeigit = resolveSize(mHeigit, heightMeasureSpec);
        int size = Math.min(mWidth, mHeigit);
        mWidth = size;
        mHeigit = size;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.save();

        canvas.translate(mWidth >> 1, mHeigit >> 1);

        canvas.drawLine(pos_left[0] + STROKE_WIDTH, pos_left[1], pos_right[0] - STROKE_WIDTH, pos_right[1], mLinePaint);//左右直线
        canvas.drawLine(pos_top[0], pos_top[1] + STROKE_WIDTH, pos_bottom[0], pos_bottom[1] - STROKE_WIDTH, mLinePaint);//上下直线

        canvas.drawPath(mBigCirclePath, mCirclePaint);//绘制大圆
        canvas.drawPath(mLittleCirclePath, mCirclePaint);//绘制小圆

        canvas.concat(matrix);
        canvas.drawCircle((pos_right[0] - pos_left[0]) / 2 + pos_left[0], pos_right[1], (pos_right[0] - pos_left[0]) / 2, mShaderPaint);


        canvas.restore();
    }


    private void init() {
        matrix = new Matrix();

        mCirclePaint = new Paint();
        mCirclePaint.setDither(true);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(1);
        mCirclePaint.setColor(Color.GRAY);

        mLinePaint = new Paint();
        mLinePaint.setDither(true);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(0.4f);
        mLinePaint.setColor(Color.GRAY);

        mBigCirclePath = new Path();
        mBigCirclePath.addCircle(mWidth >> 1, mHeigit >> 1, 140/*mWidth / 2.0f*/, Path.Direction.CW);
        mLittleCirclePath = new Path();
        mLittleCirclePath.addCircle(mWidth >> 1, mHeigit >> 1, 75/*mWidth / 2.0f / 2.0f*/, Path.Direction.CW);


        pos_left = new float[2];
        pos_right = new float[2];
        pos_top = new float[2];
        pos_bottom = new float[2];

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mBigCirclePath, true);
        length = mPathMeasure.getLength();
        /*****左右过圆心的点，可以确定一条过圆心的横向的直线*****/
        mPathMeasure.getPosTan(length / 2, pos_left, null);//获得圆左边的点，
        mPathMeasure.getPosTan(0, pos_right, null);//获得圆右边的点
        /*****上下两点确定过圆心的竖向直线******/
        mPathMeasure.getPosTan(length / 2 + length / 4, pos_top, null);//获得圆最上面的点
        mPathMeasure.getPosTan(length / 4, pos_bottom, null);//获得圆最下面的点

        mShader = new SweepGradient((pos_right[0] - pos_left[0]) / 2 + pos_left[0], pos_right[1], Color.TRANSPARENT, Color.parseColor("#8CC6D1"));
        mShaderPaint = new Paint();
        mShaderPaint.setDither(true);
        mShaderPaint.setAntiAlias(true);
        mShaderPaint.setColor(0x209DBB00);
        mShaderPaint.setShader(mShader);
        start();
    }

    public void start() {
        mThread = new VirusScan.ScanThread(this);
        mThread.setName("radar");
        mThread.start();
        threadRunning = true;
        isstart = true;
    }

    public void stop() {
        if (isstart) {
            threadRunning = false;
            isstart = false;
        }
    }

    protected class ScanThread extends Thread {

        private VirusScan view;

        public ScanThread(VirusScan view) {
            // TODO Auto-generated constructor stub
            this.view = view;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (threadRunning) {
                if (isstart) {
                    view.post(new Runnable() {
                        public void run() {
                            start = start + 1;
                            matrix = new Matrix();
                            //设定旋转角度,进行转圈操作的圆心
                            matrix.preRotate(direction * start - 90, (pos_right[0] - pos_left[0]) / 2 + pos_left[0], pos_right[1]);
                            view.invalidate();

                        }
                    });
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
