package com.testanim.wanghailong.testpropertyanim.customerClass;

import android.animation.TypeEvaluator;

/**
 * Created by wanghailong on 2018/5/27.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float v, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + v * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + v * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
