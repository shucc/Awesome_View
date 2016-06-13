package org.cchao.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.cchao.awesome.R;

/**
 * Created by chenchao on 16/5/30.
 * cc@cchao.org
 * 横向进度条
 */
public class HorizontalProgressView extends View {

    private int width;
    private int height;

    private int horizontalColor;

    private int horizontalProgressColor;

    private Paint paint;
    private RectF rectF;

    private int progress = 0;

    public HorizontalProgressView(Context context) {
        this(context, null);
    }

    public HorizontalProgressView(Context context, AttributeSet atts) {
        this(context, atts, 0);
    }

    public HorizontalProgressView(Context context, AttributeSet atts, int defStyle) {
        super(context, atts, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(atts, R.styleable.HorizontalProgressView, defStyle, 0);
        horizontalColor = typedArray.getColor(R.styleable.HorizontalProgressView_horizontalColor, Color.WHITE);
        horizontalProgressColor = typedArray.getColor(R.styleable.HorizontalProgressView_horizontalProgressColor, Color.TRANSPARENT);
        typedArray.recycle();

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth();
        height = getHeight();

        paint.setStrokeWidth(height);

        rectF = new RectF(0, 0, width, height);
        paint.setColor(horizontalColor);
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);
        paint.setColor(horizontalProgressColor);
        int degree = progress * width / 100;
        rectF = new RectF(0, 0, degree, height);
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);
    }

    public synchronized void setProgress(int progress) {
        if(progress < 0){
            throw new IllegalArgumentException("进度值不能小于0");
        }
        if(progress > 100){
            progress = 100;
        }
        if(progress <= 100){
            this.progress = progress;
            postInvalidate();
        }
    }

    public int getProgress() {
        return progress;
    }

    public int getHorizontalColor() {
        return horizontalColor;
    }

    public void setHorizontalColor(int horizontalColor) {
        this.horizontalColor = horizontalColor;
    }

    public int getHorizontalProgressColor() {
        return horizontalProgressColor;
    }

    public void setHorizontalProgressColor(int horizontalProgressColor) {
        this.horizontalProgressColor = horizontalProgressColor;
    }
}
