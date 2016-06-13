package org.cchao.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.cchao.awesome.R;

/**
 * Created by chenchao on 16/2/17.
 * cc@cchao.org
 * 圆形进度条
 */
public class CircleProgressView extends View {

    private int mWidth;
    private int mHeight;

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

    //进度条圆环总角度
    private int mCircleAngle;

    //进度条开始角度
    private int mCicrcleStartAngle;

    private Paint mPaint;

    private Paint mTextPaint;

    private Rect mTextBounds;

    //进度
    private int mProgress = 0;

    //是否计算文字大小
    private boolean isCalculate = false;

    private RectF mRectF;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet atts) {
        this(context, atts, 0);
    }

    public CircleProgressView(Context context, AttributeSet atts, int defStyle) {
        super(context, atts, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(atts, R.styleable.CircleProgressView, defStyle, 0);
        mCircleColor = typedArray.getColor(R.styleable.CircleProgressView_circleColor, Color.TRANSPARENT);
        mCircleProgressColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressColor, Color.BLUE);
        //进度条宽度默认设置为5dp
        mCircleWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleWidth, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        //进度文本显示大小默认为16sp
        mCircleTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_circleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mCircleTextColor = typedArray.getColor(R.styleable.CircleProgressView_circleTextColor, Color.BLACK);
        mCircleAngle = typedArray.getInteger(R.styleable.CircleProgressView_circleAngle, 360);
        mCicrcleStartAngle = typedArray.getInteger(R.styleable.CircleProgressView_circleStartAngle, 0);
        typedArray.recycle();

        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextBounds = new Rect();
        mTextPaint.setTextSize(mCircleTextSize);
        //设置文本最大宽度，即100%的时候
        String temp = "100%";
        mTextPaint.getTextBounds(temp, 0, temp.length(), mTextBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //根据文本大小确定宽高
        double desireByTextWidth = getPaddingLeft() + mTextBounds.width() + getPaddingRight() + mCircleWidth * 2;
        double desireByTextHeight = getPaddingTop() + mTextBounds.height() + getPaddingBottom() + mCircleWidth * 2;
        double desire = Math.max(desireByTextWidth, desireByTextHeight);

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                mWidth = (int)desire;
            }
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = (int)desire;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2;
        int radius = center - mCircleWidth / 2;

        //计算一次文字大小
        if (!isCalculate) {
            String temp = "100%";
            //将文本rect对角线的一半与半径比较
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
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mRectF = new RectF(center - radius, center - radius, center + radius, center + radius);

        mPaint.setColor(mCircleColor);
        canvas.drawArc(mRectF, mCicrcleStartAngle, mCircleAngle, false, mPaint);
        //canvas.drawCircle(center, center, radius, mPaint);
        mPaint.setColor(mCircleProgressColor);
        int degree = mProgress * mCircleAngle / 100;
        canvas.drawArc(mRectF, mCicrcleStartAngle, degree, false, mPaint);

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

    public int getmCicrcleStartAngle() {
        return mCicrcleStartAngle;
    }

    public void setmCicrcleStartAngle(int mCicrcleStartAngle) {
        this.mCicrcleStartAngle = mCicrcleStartAngle;
    }

    public int getmCircleAngle() {
        return mCircleAngle;
    }

    public void setmCircleAngle(int mCircleAngle) {
        this.mCircleAngle = mCircleAngle;
    }

    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public int getmCircleProgressColor() {
        return mCircleProgressColor;
    }

    public void setmCircleProgressColor(int mCircleProgressColor) {
        this.mCircleProgressColor = mCircleProgressColor;
    }

    public int getmCircleTextColor() {
        return mCircleTextColor;
    }

    public void setmCircleTextColor(int mCircleTextColor) {
        this.mCircleTextColor = mCircleTextColor;
    }

    public int getmCircleTextSize() {
        return mCircleTextSize;
    }

    public void setmCircleTextSize(int mCircleTextSize) {
        this.mCircleTextSize = mCircleTextSize;
    }

    public int getmCircleWidth() {
        return mCircleWidth;
    }

    public void setmCircleWidth(int mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
    }
}
