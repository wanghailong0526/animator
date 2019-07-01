package com.testanim.wanghailong.testpropertyanim.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import android.util.AttributeSet;

import com.testanim.wanghailong.testpropertyanim.R;

/**
 * @date : 2019-06-28 15:43
 * @author: wanghailong
 * @description: edittext:支持 单独设置hint text style, hint text size
 * <p>
 * app:hintTextSize="@dimen/dimen_14"
 * app:hintTextStyle="normal"
 */
public class EditTextHint extends android.support.v7.widget.AppCompatEditText {
    private Context mContext;
    private String TAG = "EditTextHint";
    private int mHintTextStyle;
    private float mHintTextSize;
    private CustomHint mCustomHint;

    public EditTextHint(Context context) {
        this(context, null);
    }

    public EditTextHint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextHint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextHint);
        mHintTextStyle = a.getInt(R.styleable.EditTextHint_hintTextStyle, Typeface.NORMAL);
        mHintTextSize = a.getDimension(R.styleable.EditTextHint_hintTextSize, 14);
        a.recycle();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getHint() != null && mCustomHint == null) {
            mCustomHint = new CustomHint(Typeface.defaultFromStyle(mHintTextStyle), getHint(), mHintTextSize);
            setHint(mCustomHint);
        }
    }

    /**
     * 使用方法
     * Typeface newTypeface = Typeface.createFromAsset(getAssets(), "AguafinaScript-Regular.ttf");
     * CustomHint customHint = new CustomHint(newTypeface, "Enter some text", Typeface.BOLD_ITALIC, 60f);
     * CustomHint customHint = new CustomHint(newTypeface, "Enter some text", Typeface.BOLD_ITALIC);
     * CustomHint customHint = new CustomHint(newTypeface, "Enter some text", 60f);
     * CustomHint customHint = new CustomHint("Enter some text", Typeface.BOLD_ITALIC, 60f);
     * CustomHint customHint = new CustomHint("Enter some text", Typeface.BOLD_ITALIC);
     * CustomHint customHint = new CustomHint("Enter some text", 60f);
     * customEditText.setHint(customHint);
     */
    public static class CustomHint extends SpannableString {
        public CustomHint(final CharSequence source, final int style) {
            this(null, source, style, null);
        }

        public CustomHint(final CharSequence source, final Float size) {
            this(null, source, size);
        }

        public CustomHint(final CharSequence source, final int style, final Float size) {
            this(null, source, style, size);
        }

        public CustomHint(final Typeface typeface, final CharSequence source, final int style) {
            this(typeface, source, style, null);
        }

        public CustomHint(final Typeface typeface, final CharSequence source, final Float size) {
            this(typeface, source, null, size);
        }

        public CustomHint(final Typeface typeface, final CharSequence source, final Integer style, final Float size) {
            super(source);

            MetricAffectingSpan typefaceSpan = new CustomMetricAffectingSpan(typeface, style, size);
            setSpan(typefaceSpan, 0, source.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    public static class CustomMetricAffectingSpan extends MetricAffectingSpan {
        private final Typeface _typeface;
        private final Float _newSize;
        private final Integer _newStyle;

        public CustomMetricAffectingSpan(Float size) {
            this(null, null, size);
        }

        public CustomMetricAffectingSpan(Float size, Integer style) {
            this(null, style, size);
        }

        public CustomMetricAffectingSpan(Typeface type, Integer style, Float size) {
            this._typeface = type;
            this._newStyle = style;
            this._newSize = size;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            applyNewSize(ds);
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            applyNewSize(paint);
        }

        private void applyNewSize(TextPaint paint) {
            if (this._newStyle != null)
                paint.setTypeface(Typeface.create(this._typeface, this._newStyle));
            else
                paint.setTypeface(this._typeface);

            if (this._newSize != null)
                paint.setTextSize(this._newSize);
        }
    }
}
