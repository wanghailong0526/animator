package com.testanim.wanghailong.testpropertyanim.customview.paint;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;


/**
 * @date : 4/21/21 10:39 AM
 * @author: wanghailong
 * @description: 额度控件
 */
public class AmountArcProgress extends View {
    private int mStrokeWidthBgArc = 0;//背景弧宽
    private int mStrokeWidthArc = 0;//进度弧的宽
    private float mStrokeWidthBgArcHalf = .0f;//背景弧宽的一半
    private final int default_Bg_Arc_color = Color.parseColor("#D57A2D");//背景弧颜色
    private final int default_Current_color = Color.parseColor("#F7F7F7");//当前进度弧颜色

    private int mDash = 15;//内圈圆弧距离外圈圆弧距离
    private int mScaleShort = 20;//短刻度长度
    private int mScaleLong = 50;//长刻度长度
    private int mScaleStrokeWidh = 1;//刻度的宽度
    private int mScalePointOffset = 10;//刻度末端的小圆点
    private int mScaleCount = 20;//刻度总数
    private float rotateAngle = 0;//旋转角度

    private Paint mPaintBgArc = null;//背景弧画笔
    private Paint mPaintCurrentArc = null;//当前进度弧画笔
    private Paint mPaintInnerArc = null;//内部圆弧画笔
    private Paint mPaintScale = null;//刻度画笔
    private Paint mPaintText = null;//文字画笔

    private RectF mRectF = null;
    private RectF mInnerRectF = null;

    private int mMaxAmount = 0;//最大额度
    private int mAvailableAmount = 0;//可用额度
    /**
     * 圆心额度上面的文案
     * 1.未登录，未获取额度 显示"额度高达(元)"
     * 2.有额度   显示 "当前可借(元)"
     * 3.额度低于 1000 显示 "当前可借(元)"
     * 4.额度用完 隐藏view
     */
    private String mAmountTipText = "";//圆心额度上面的文案

    /**圆心额度文案逻辑
     * 1.未登录，未获取额度 显示最大可借额度
     * 2.有额度 显示 当前可用额度值
     * 3.额度低于1000 显示 当前可用额度值
     * 4.额度用完 显示 "额度已用完"
     */

    /**
     * loanTotalAmount:圆心文案下面显示的文案
     * 1.未登录，未获取额度  隐藏view
     * 2.有额度  显示"总额度XXX元"
     * 3.额度低于1000   显示"总额度XXX元"
     * 4.4.额度用完 显示"总额度XXX元"
     */
    private String mTotalAmountText = "";//圆心额度文案下面显示的文案

    /******此时一个圆的圆周为360*0.6666f*****/
    private float mArcAngle = 360 * 0.65f;
    private float mStartAngle = 0;//开始角度(从哪开始转)
    private float mSweepAngle = 1.0f;//旋转角度，旋转多少度
    private int minWidth = 200;//默认宽
    private int minHeight = 200;//默认高
    private float mProgress = 0;//当前进度
    private int mScaleoffset = 0;//刻度偏移量

    public float getmProgress() {
        return mProgress;
    }

    public AmountArcProgress(Context context) {
        this(context, null);
    }

    public AmountArcProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmountArcProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        minWidth = dipTopx(minWidth);
        minHeight = dipTopx(minHeight);

        //背景圆弧
        mStrokeWidthBgArc = dipTopx(18);
        mStrokeWidthBgArcHalf = mStrokeWidthBgArc / 2.0f;
        mPaintBgArc = new Paint();
        mPaintBgArc.setStrokeWidth(mStrokeWidthBgArc);
        mPaintBgArc.setAntiAlias(true);
        mPaintBgArc.setDither(true);
        mPaintBgArc.setStyle(Paint.Style.STROKE);
        mPaintBgArc.setStrokeCap(Paint.Cap.ROUND);
        mPaintBgArc.setColor(default_Bg_Arc_color);

        //进度圆弧
        mStrokeWidthArc = dipTopx(14);
        mPaintCurrentArc = new Paint();
        mPaintCurrentArc.setStrokeWidth(mStrokeWidthArc);
        mPaintCurrentArc.setAntiAlias(true);
        mPaintCurrentArc.setDither(true);
        mPaintCurrentArc.setStyle(Paint.Style.STROKE);
        mPaintCurrentArc.setStrokeCap(Paint.Cap.ROUND);
        mPaintCurrentArc.setColor(default_Current_color);

        //内部圆弧
        mPaintInnerArc = new Paint();
        mPaintInnerArc.setStrokeWidth(1);
        mPaintInnerArc.setAntiAlias(true);
        mPaintInnerArc.setDither(true);
        mPaintInnerArc.setStyle(Paint.Style.STROKE);
        mPaintInnerArc.setStrokeCap(Paint.Cap.ROUND);
        mPaintInnerArc.setColor(default_Current_color);

        //刻度
        mPaintScale = new Paint();
        mPaintScale.setStrokeWidth(mScaleStrokeWidh);
        mPaintScale.setAntiAlias(true);
        mPaintScale.setDither(true);
        mPaintScale.setStyle(Paint.Style.STROKE);
        mPaintScale.setStrokeCap(Paint.Cap.ROUND);
        mPaintScale.setColor(default_Current_color);

        //文字
        mPaintText = new TextPaint();
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setColor(default_Current_color);
        mPaintText.setTextSize(dipTopx(11));
        mPaintText.setTextAlign(Paint.Align.CENTER);
//        mPaintText.setTypeface(CustomTextFontUtils.getInstance().getBoldFont());//设置字体

        //起始角度为270度减去整个圆的一半,顺时针旋转
        mStartAngle = (270 - mArcAngle / 2.0f);
        mScaleoffset = mStrokeWidthBgArc + mDash + mScaleStrokeWidh + 6;
        rotateAngle = (float) (mArcAngle / (mScaleCount * 1.0));

        mRectF = new RectF();
        mInnerRectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        minWidth = 200;
        minHeight = 200;
        minWidth = dipTopx(minWidth);
        minHeight = dipTopx(minHeight);
        minWidth += getPaddingLeft() + getPaddingRight();
        minHeight += getPaddingTop() + getPaddingBottom();

        if (widthMode == MeasureSpec.AT_MOST) {//宽为wrap_content
            if (heightMode == MeasureSpec.EXACTLY) {//高为match_parent或确切值
                width = height;
            } else {//宽高都是wrap_content,取默认宽高
                width = minWidth;
                height = minHeight;
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {//宽为match_parent或确切值
            if (heightMode == MeasureSpec.AT_MOST) {//高为match_parent或确切值
                height = width;
            }
        } else {
            width = minWidth;
            height = minHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w == 0 || h == 0) {
            return;
        }
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = getPaddingLeft() + mStrokeWidthBgArcHalf;
        mRectF.top = getPaddingTop() + mStrokeWidthBgArcHalf;
        mRectF.right = getMeasuredWidth() - getPaddingRight() - mStrokeWidthBgArcHalf;
        mRectF.bottom = getMeasuredHeight() - getPaddingBottom() - mStrokeWidthBgArcHalf;

        mInnerRectF.left = mStrokeWidthBgArc + mDash;
        mInnerRectF.top = mStrokeWidthBgArc + mDash;
        mInnerRectF.right = getMeasuredWidth() - mStrokeWidthBgArc - mDash;
        mInnerRectF.bottom = getMeasuredHeight() - mStrokeWidthBgArc - mDash;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        //绘制背景弧
        drawBgArc(canvas);

        //绘制内圈弧
        drawInnerArc(canvas);

        //绘制刻度
        drawScale(canvas);

        //绘制文字 最多有5个位置有文字
        drawText(canvas);

        //绘制当前进度弧
        drawCurrrentArc(canvas);

        canvas.restore();
    }

    //绘制背景弧
    private void drawBgArc(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mArcAngle, false, mPaintBgArc);
    }

    //绘制内圈弧
    private void drawInnerArc(Canvas canvas) {
        canvas.drawArc(mInnerRectF, mStartAngle, mArcAngle, false, mPaintInnerArc);
    }

    //绘制刻度
    private void drawScale(Canvas canvas) {
        canvas.save();

        mPaintScale.setStyle(Paint.Style.STROKE);
        //将最右端旋转到顶部(向左旋转总刻度一半的度数)
        canvas.rotate(rotateAngle * (mScaleCount / 2), mInnerRectF.centerX(), mInnerRectF.centerY());

        //向右旋转 画刻度
        for (int i = 0; i <= mScaleCount; i++) {
            if (i % 10 == 0) {
                mPaintScale.setStyle(Paint.Style.STROKE);
                canvas.drawLine(mInnerRectF.centerX(), mScaleoffset, mInnerRectF.centerX(), mScaleoffset + mScaleLong, mPaintScale);
            } else {
                mPaintScale.setStyle(Paint.Style.FILL);
                canvas.drawLine(mInnerRectF.centerX(), mScaleoffset, mInnerRectF.centerX(), mScaleoffset + mScaleShort, mPaintScale);
                canvas.drawCircle(mInnerRectF.centerX(), mScaleoffset + mScaleShort + mScalePointOffset, 1, mPaintScale);
            }
            canvas.rotate(-rotateAngle, mInnerRectF.centerX(), mInnerRectF.centerY());
        }

        canvas.restore();
    }

    //绘制文字 最多5个位置有文字
    private void drawText(Canvas canvas) {
        mPaintText.setTypeface(null);
        //圆心额度文案的宽高
        if (mAvailableAmount == 0) {//额度用完
            mPaintText.setTextSize(dipTopx(26));
        } else {
            mPaintText.setTextSize(dipTopx(48));
        }
        Paint.FontMetrics fontMetricsAvailableAmount = mPaintText.getFontMetrics();

        //1.绘制最小额度0在圆弧左端
        String zero = "0";
        mPaintText.setColor(Color.parseColor("#FFFFFF"));
        mPaintText.setTextSize(dipTopx(12));
        float mTextWidth = mPaintText.measureText(zero);
        float x = (float) (mRectF.centerX() + getMeasuredWidth() / 2 * Math.cos(Math.toRadians(mStartAngle)));
        float y = (float) (mRectF.centerY() + getMeasuredWidth() / 2 * Math.sin(Math.toRadians(mStartAngle)));
        canvas.drawText(zero, x + mTextWidth / 2 + mStrokeWidthBgArcHalf, y + mStrokeWidthBgArc, mPaintText);

        //2.绘制最大额度在圆弧右端
        mPaintText.setColor(Color.parseColor("#FFFFFF"));
        mPaintText.setTextSize(dipTopx(12));
        float angle = 270 + mArcAngle / 2.0f;
        x = (float) (mRectF.centerX() + getMeasuredWidth() / 2 * Math.cos(Math.toRadians(angle)));
        y = (float) (mRectF.centerY() + getMeasuredWidth() / 2 * Math.sin(Math.toRadians(angle)));
        float tempY = y;//圆心额度下面的文案需要使用这个坐标
        canvas.drawText(mMaxAmount + "", x - mStrokeWidthBgArcHalf, y + mStrokeWidthBgArc, mPaintText);

        //3.绘制圆心额度上面的文案
        mPaintText.setColor(Color.parseColor("#F2EDE9"));
        mPaintText.setTextSize(dipTopx(12));
        x = mRectF.centerX();
        y = mRectF.centerY() - ((fontMetricsAvailableAmount.bottom - fontMetricsAvailableAmount.top / 2) - fontMetricsAvailableAmount.top) / 2;
        canvas.drawText(mAmountTipText, x, y, mPaintText);

        //4.绘制圆心额度文案
        mPaintText.setColor(Color.parseColor("#FFFFFF"));
        x = mRectF.centerX();
        y = getMeasuredHeight() / 2.0f - (fontMetricsAvailableAmount.bottom - fontMetricsAvailableAmount.top) / 2 - fontMetricsAvailableAmount.top;
        if (mAvailableAmount == 0) {//额度用完
            mPaintText.setTextSize(dipTopx(26));
            mPaintText.setTypeface(null);
            canvas.drawText("额度已用完", x, y, mPaintText);
        } else {
//            mPaintText.setTypeface(CustomTextFontUtils.getInstance().getBoldFont());//设置字体
            mPaintText.setTextSize(dipTopx(48));
            canvas.drawText(((int) mProgress) + "", x, y, mPaintText);
        }

        //5.绘制圆心下面的总额度文案
        mPaintText.setTypeface(null);
        mPaintText.setColor(Color.parseColor("#FFFFFF"));
        mPaintText.setTextSize(dipTopx(14));
        x = mRectF.centerX();
        canvas.drawText(mTotalAmountText, x, tempY, mPaintText);
    }

    //绘制当前进度弧
    private void drawCurrrentArc(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mPaintCurrentArc);
    }

    /**
     * 设置数据
     *
     * @param max
     * @param available
     */
    public void setData(int max, int available, String amountTipText, String totalAmountText) {
//        if (mMaxAmount != max || mAvailableAmount != available || !mAmountTipText.equals(amountTipText) || !mTotalAmountText.equals(totalAmountText)) {//数据有变化刷新
        this.mMaxAmount = max;
        this.mAvailableAmount = available;
        this.mAmountTipText = amountTipText;
        this.mTotalAmountText = totalAmountText;

        setVisibility(VISIBLE);
        startAnimator();
//        }
    }

    public void setAmountTipText(String amountTipText) {
        this.mAmountTipText = amountTipText;
    }

    public void setTotalAmountText(String totalAmountText) {
        this.mTotalAmountText = totalAmountText;
    }

    /**
     * 开始动画
     */
    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mAvailableAmount);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                mSweepAngle = (mProgress / mMaxAmount * mArcAngle) + 0.5f;
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    private int dipTopx(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }

    private int pxTodp(int pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, getResources().getDisplayMetrics());
    }
}

