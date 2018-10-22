package com.testanim.wanghailong.testpropertyanim.customerClass;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by wanghailong on 2018/7/28.
 */

public class SerfaceViewTempalte extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder mHolder;
    private Thread mDrawableThread;//serfaceview的绘制线程
    private boolean isRunnable;//是否运行，用于控制线程
    private Canvas mCanvas;//用于绘制

    public SerfaceViewTempalte(Context context) {
        super(context);
        init();
    }

    public SerfaceViewTempalte(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SerfaceViewTempalte(Context context, AttributeSet attrs, int defStyleAttr) {
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
    public void surfaceCreated(SurfaceHolder holder) {
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
        /*****ServiceViewestroyed停止绘制*****/
        isRunnable = false;
    }

    @Override
    public void run() {
        /*****若isRunnable为true*****/
        while (isRunnable) {
            draw();
        }
    }

    /*****绘制方法*****/
    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //draw something
            }

        } catch (Exception e) {
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }
}
