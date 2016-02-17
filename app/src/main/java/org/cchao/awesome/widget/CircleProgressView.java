package org.cchao.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.cchao.awesome.R;

/**
 * Created by chenchao on 16/2/17.
 */
public class CircleProgressView extends View {

    //圆环颜色
    private int mCircleColor;

    //进度条颜色
    private int mCircleProgressColor;

    //进度条宽度
    private int mCircleWidth;

    //进度显示大小
    private int mCircleTextSize;

    //进度显示颜色
    private int mCircleTextColor;

    private Paint mPaint;

    private Paint mTextPaint;

    private Rect mTextBounds;

    //进度
    private int mProgress = 0;

    //是否计算文字大小
    private boolean isCalculate = false;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet atts) {
        this(context, atts, 0);
    }

    public CircleProgressView(Context context, AttributeSet atts, int defStyle) {
        super(context, atts, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(atts, R.styleable.CircleProgressView, defStyle, 0);
        mCircleColor = typedArray.getColor(R.styleable.CircleProgressView_circleColor, defStyle);
        mCircleProgressColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressColor, defStyle);
        //进度条宽度默认设置为5dp
        mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //进度文本显示大小默认为16sp
        mCircleTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mCircleTextColor = typedArray.getColor(R.styleable.CircleProgressView_circleTextColor, defStyle);
        typedArray.recycle();

        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextBounds = new Rect();
        mTextPaint.setTextSize(mCircleTextSize);
        String temp = "100%";
        mTextPaint.getTextBounds(temp, 0, temp.length(), mTextBounds);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;

        if (!isCalculate) {
            String temp = "100%";
            //将文本对角线的一半与半径比较
            double radiusPow = Math.pow(radius - mCircleWidth / 2, 2);
            double otherPow = Math.pow((double)mTextBounds.width()/2, 2) + Math.pow((double)mTextBounds.height()/2, 2);
            //如果文本宽度超出内圆，不断减小文字大小
            while (radiusPow < otherPow) {
                otherPow = Math.pow((double)mTextBounds.width()/2, 2) + Math.pow((double)mTextBounds.height()/2, 2);
                mCircleTextSize--;
                mTextPaint.setTextSize(mCircleTextSize);
                mTextPaint.getTextBounds(temp, 0, temp.length(), mTextBounds);
            }
            isCalculate = true;
        }

        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);

        mPaint.setColor(mCircleColor);
        canvas.drawCircle(center, center, radius, mPaint);
        mPaint.setColor(mCircleProgressColor);
        int degree = mProgress * 360 / 100;
        canvas.drawArc(rectF, -90, degree, false, mPaint);

        mTextPaint.setColor(mCircleProgressColor);
        float textWidth = mTextPaint.measureText(mProgress + "%");
        canvas.drawText(mProgress + "%", center - textWidth / 2, center + mTextBounds.height() / 2, mTextPaint);
    }

    public synchronized int getProgress() {
        return mProgress;
    }

    public synchronized void setProgress(int mProgress) {
        if(mProgress < 0){
            throw new IllegalArgumentException("进度值不能小于0");
        }
        if(mProgress > 100){
            mProgress = 100;
        }
        if(mProgress <= 100){
            this.mProgress = mProgress;
            postInvalidate();
        }
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getCircleProgressColor() {
        return mCircleProgressColor;
    }

    public void setCircleProgressColor(int mCircleProgressColor) {
        this.mCircleProgressColor = mCircleProgressColor;
    }

    public int getCircleTextColor() {
        return mCircleTextColor;
    }

    public void setCircleTextColor(int mCircleTextColor) {
        this.mCircleTextColor = mCircleTextColor;
    }

    public int getCircleTextSize() {
        return mCircleTextSize;
    }

    public void setCircleTextSize(int mCircleTextSize) {
        this.mCircleTextSize = mCircleTextSize;
    }

    public int getCircleWidth() {
        return mCircleWidth;
    }

    public void setCircleWidth(int mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
    }
}
