package org.cchao.awesome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chenchao on 16/11/16.
 * cc@cchao.org
 */
public class TestView extends View {

    private Paint paint = new Paint();

    private int width;
    private int height;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
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
        canvas.translate(width >> 1, height >> 1);
        Path path = new Path();
        Path src = new Path();
        path.addRect(-200 ,-200, 200, 200, Path.Direction.CW);
        src.addCircle(0, 0, 100, Path.Direction.CW);
        path.addPath(src);
        canvas.drawPath(path, paint);
    }
}
