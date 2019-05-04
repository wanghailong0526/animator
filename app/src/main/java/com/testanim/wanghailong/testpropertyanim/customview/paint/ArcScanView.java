package com.testanim.wanghailong.testpropertyanim.customview.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @author : wanghailong
 * @description: 扇形扫描的view, 扇形的尾部跟随扫描
 */
public class ArcScanView extends View {
    private int mStrokeWidth = 6;
    private Paint mPaint;
    private RectF mRectF;
    private int mSize;
    private Path mPath;
    private PathMeasure mPathMeasure; //路径计算

    private float pathDistanceRatio; //路径长度的比值 (0 - 1)

    private float mPathMeasureLength;
    public static final String TAG = "whl:**";

    public ArcScanView(Context context) {
        this(context, null);
    }

    public ArcScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.battery_optimize_scan_bg_BG));
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mRectF = new RectF();
        mPath = new Path();
        mPathMeasure = new PathMeasure();
        System.out.println(TAG + " init");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println(TAG + " onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        mSize = Math.min(width, height);
        setMeasuredDimension(mSize, mSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println(TAG + " onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = mStrokeWidth / 2;
        mRectF.top = mStrokeWidth / 2;
        mRectF.right = mSize - mStrokeWidth / 2;
        mRectF.bottom = mSize - mStrokeWidth / 2;
        mPath.reset();
        mPath.addCircle(mRectF.width() / 2.0f, mRectF.height() / 2.0f, mRectF.width() / 2, Path.Direction.CW);
        mPathMeasure.setPath(mPath, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println(TAG + " onDraw");
        canvas.save();
        mPathMeasureLength = mPathMeasure.getLength();
        float stopD = mPathMeasureLength * pathDistanceRatio; //当前截取的结束点
        float startD = (float) (stopD - ((0.5 - Math.abs(pathDistanceRatio - 0.5)) * (mRectF.right + mRectF.bottom))); //当前截取的开始点

        Path dst = new Path();
        dst.reset();
        dst.lineTo(0, 0);

        mPaint.setStyle(Paint.Style.FILL);
        mPathMeasure.getSegment(startD, stopD, dst, true);

        dst.lineTo(mSize / 2, mSize / 2);//移动到圆心形成扇形，配合Paint.Style.FILL
        canvas.rotate(-90, mSize / 2, mSize / 2);
        canvas.drawPath(dst, mPaint);

        canvas.restore();
    }


    /**
     * 通过外界设置值，valueanimtor.ofFloat(0,1)
     *
     * @param pathDistanceRatio
     */
    public void setRatio(float pathDistanceRatio) {
        this.pathDistanceRatio = pathDistanceRatio;
        postInvalidate();
    }

    public void reset() {
        pathDistanceRatio = 0;
        postInvalidate();
    }
}
