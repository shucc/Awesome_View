package org.cchao.awesome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import org.cchao.awesome.R;

import java.util.Random;

/**
 * Created by chenchao on 16/6/12.
 * cc@cchao.org
 * 验证码
 */
public class CodesView extends View {

    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private int width;
    private int height;

    //验证码字体颜色
    private int codeTextColor;

    //验证码字体大小
    private int codeTextSize;

    //验证码数目
    private int codeTextNum;

    //遮盖线颜色
    private int lineColor;

    //遮盖线宽度
    private int lineSize;

    //遮盖线数目
    private int lineNum;

    //遮盖点颜色
    private int pointColor;

    //遮盖点数目
    private int pointNum;

    //验证码
    private String code;

    private Paint paint;
    private Paint textPaint;
    private Rect textRect;

    private Random random;

    public CodesView(Context context) {
        this(context, null);
    }

    public CodesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CodesView, defStyle, 0);
        codeTextColor = typedArray.getColor(R.styleable.CodesView_codeTextColor, Color.parseColor("#c13a2c"));
        codeTextSize = typedArray.getDimensionPixelSize(R.styleable.CodesView_codeTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        codeTextNum = typedArray.getInteger(R.styleable.CodesView_codeTextNum, 4);
        lineColor = typedArray.getColor(R.styleable.CodesView_codeLineColor, Color.RED);
        lineSize = typedArray.getDimensionPixelSize(R.styleable.CodesView_codeLineSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        lineNum = typedArray.getInteger(R.styleable.CodesView_codeLineNum, 1);
        pointColor = typedArray.getColor(R.styleable.CodesView_codePointColor, Color.BLACK);
        pointNum = typedArray.getInteger(R.styleable.CodesView_codePointNum, 1);
        typedArray.recycle();

        paint = new Paint();
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textRect = new Rect();
        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        code = createCode();
        width = getWidth();
        height = getHeight();
        int widthEach = width / codeTextNum;
        //画验证码字符
        for (int i = 0; i < code.length(); i++) {
            textRect = new Rect(widthEach * i, 0, widthEach * (i + 1), height);
            textPaint.setColor(Color.TRANSPARENT);
            canvas.drawRect(textRect, textPaint);
            Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
            int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            // 下面这行是实现水平居中，drawText传入targetRect.centerX()
            textPaint.setTextAlign(Paint.Align.CENTER);
            float skewX = random.nextInt(11) / 10;
            skewX = random.nextBoolean() ? skewX : -skewX;
            textPaint.setTextSkewX(skewX);
            textPaint.setColor(codeTextColor);
            textPaint.setTextSize(codeTextSize);
            canvas.drawText(code.charAt(i) + "", textRect.centerX(), baseline, textPaint);
        }
        //画遮盖线
        for (int i = 0; i < lineNum; i++) {
            drawLine(canvas, paint);
        }
        //画遮盖点
        for (int i = 0; i < pointNum; i++) {
            drawPoint(canvas, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            postInvalidate();
        }
        return true;
    }

    /**
     * 画遮盖线
     * @param canvas
     * @param paint
     */
    private void drawLine(Canvas canvas, Paint paint) {
        int startX = 0;
        int startY = random.nextInt(height);
        int stopX = width;
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(lineSize);
        paint.setColor(lineColor);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    /**
     * 画遮盖点
     * @param canvas
     * @param paint
     */
    private void drawPoint(Canvas canvas, Paint paint) {
        paint.setColor(pointColor);
        canvas.drawPoint(random.nextInt(width), random.nextInt(height), paint);
    }

    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeTextNum; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    public String getCode() {
        return code;
    }
}
