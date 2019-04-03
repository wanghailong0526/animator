package com.testanim.wanghailong.testpropertyanim.customview.paint.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author : wanghailong
 * @description:
 */
public class ScrollViewGroup extends ViewGroup {
    private Scroller mScroller;
    private float mLastX;
    private Context mContext;
    public static final String TAG = "ScrollViewGroup";

    public ScrollViewGroup(Context context) {
        this(context, null);
    }

    public ScrollViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        _init();
    }

    private void _init() {
        System.out.println(TAG + ":init");
        mScroller = new Scroller(mContext);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = (int) x;
                return true;
            case MotionEvent.ACTION_MOVE:
                int movedX = (int) (mLastX - x);
                scrollBy(movedX, 0);
                mLastX = x;

                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int sonIndex = (getScrollX() + getWidth() / 2) / getWidth();

                // 如果滑动页面超过当前页面数，那么把屏index定为最大页面数的index。
                int childCount = getChildCount();
                if (sonIndex >= childCount)
                    sonIndex = childCount - 1;

                // 现在需要滑动的相对距离。
                int dx = sonIndex * getWidth() - getScrollX();
                // Y方向不变，X方向到达目的地。
                mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
                invalidate();

                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        System.out.println(TAG + ":computeScroll");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {//如果返回true，整体滑动未结束，返回false整体滑动结束
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println(TAG + ":onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        System.out.println(TAG + ":onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        System.out.println(TAG + ":onLayout");
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                int childWidth = childView.getMeasuredWidth();
                /**
                 *
                 * 将所有的子view水平布局，依次放置
                 *
                 * left:0，1*childWidth,2*childWidth,3*childWidth
                 * top:0
                 * right:childWidth,2*childWidth,3*childWidth,4*childWidth
                 * bottom: childView.getMeasureHeight();
                 *
                 */
                int left = i * childWidth;
                int top = 0;
                int right = i * childWidth + childWidth;
                int bottom = childView.getMeasuredHeight();

                childView.layout(left, top, right, bottom);
            }
        }
    }
}
