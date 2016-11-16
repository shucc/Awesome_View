package org.cchao.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import org.cchao.awesome.R;

import java.util.ArrayList;

/**
 * Created by chenchao on 16/11/16.
 * cc@cchao.org
 */
public class RadarView extends View {

    private final int count = 6;

    private float angle = (float) (Math.PI * 2 / count);

    private float minRadius;

    private Paint paint = new Paint();
    private Paint regionPatin = new Paint();
    private Paint textPaint = new Paint();
    private Rect textRect = new Rect();

    private int textSize;

    private int width;
    private int height;

    private ArrayList<String> titles;
    private ArrayList<Integer> percentages;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RadarView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.RadarView, defStyle, 0);
        textSize = typedArray.getDimensionPixelSize(R.styleable.RadarView_titleTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        typedArray.recycle();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.BLACK);
        regionPatin.setColor(Color.BLUE);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == titles || null == percentages || titles.size() == 0 || percentages.size() == 0) {
            return;
        }
        canvas.translate(width >> 1, height >> 1);
        minRadius = Math.min(width, height) * 0.9f / (count - 1 << 1);
        drawGrid(canvas);
        drawLines(canvas);
        drawTitle(canvas);
        drawRegion(canvas);
    }

    /**
     * 画网格
     * @param canvas
     */
    private void drawGrid(Canvas canvas) {
        Path path = new Path();
        for (int i = 1; i < count; i++) {
            path.reset();
            float radius = i * minRadius;
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(radius, 0);
                } else {
                    float x = (float) (radius * Math.cos(angle * j));
                    float y = (float) (radius * Math.sin(angle * j));
                    path.lineTo(x, y);
                }
            }
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 画中心到各顶点连线
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        float radius = minRadius * (count - 1);
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(0, 0);
            float x = (float) (radius * Math.cos(angle * i));
            float y = (float) (radius * Math.sin(angle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, paint);
        }
    }

    private void drawTitle(Canvas canvas) {
        textPaint.getTextBounds(titles.get(0), 0, titles.get(0).length(), textRect);
        //TODO
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        float maxRadius = minRadius * (count - 1);
        for (int i = 0; i < count; i++) {
            float x = (float) ((maxRadius + textHeight / 2) * Math.cos(angle * i));
            float y = (float) ((maxRadius + textHeight / 2) * Math.sin(angle * i));
            if (x > 0) {
                textPaint.setTextAlign(Paint.Align.LEFT);
            } else {
                textPaint.setTextAlign(Paint.Align.RIGHT);
            }
            canvas.drawText(titles.get(i), x, y, textPaint);
        }
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        float maxRadius = minRadius * (count - 1);
        for (int i = 0; i < count; i++) {
            float radius = maxRadius * percentages.get(i) / 100;
            float x = (float) (radius * Math.cos(angle * i));
            float y = (float) (radius * Math.sin(angle * i));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            regionPatin.setStyle(Paint.Style.FILL);
            regionPatin.setAlpha(255);
            canvas.drawCircle(x, y, 10, regionPatin);
        }
        path.close();
        regionPatin.setStyle(Paint.Style.FILL_AND_STROKE);
        regionPatin.setAlpha(127);
        canvas.drawPath(path, regionPatin);
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
        invalidate();
    }

    public void setPercentages(ArrayList<Integer> percentages) {
        this.percentages = percentages;
        invalidate();
    }
}
