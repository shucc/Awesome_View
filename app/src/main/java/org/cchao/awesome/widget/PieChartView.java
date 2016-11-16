package org.cchao.awesome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.cchao.awesome.bean.PieData;

import java.util.ArrayList;

/**
 * Created by chenchao on 16/11/16.
 * cc@cchao.org
 */
public class PieChartView extends View {

    private Paint paint = new Paint();

    private ArrayList<PieData> pieDatas;

    private float startAngle = 0;

    private int width;
    private int height;

    public PieChartView(Context context) {
        super(context);
    }

    public PieChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
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
        if (null == pieDatas) {
            return;
        }
        float nowStartAngle = startAngle;
        canvas.translate(width >> 1, height >> 1);
        float r = (float) (Math.min(width, height) >> 1);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < pieDatas.size(); i++) {
            PieData pieData = pieDatas.get(i);
            paint.setColor(pieData.getColor());
            canvas.drawArc(rectF, nowStartAngle, pieData.getAngle(), true, paint);
            nowStartAngle += pieData.getAngle();
        }
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        initData(pieDatas);
        invalidate();
    }

    public void setData(ArrayList<PieData> pieDatas) {
        this.pieDatas = pieDatas;
        initData(pieDatas);
        invalidate();
    }

    private void initData(ArrayList<PieData> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        int sumValue = 0;
        for (int i = 0; i < pieDatas.size(); i++) {
            sumValue += pieDatas.get(i).getValue();
        }
        int sumAngel = 0;
        for (int i = 0; i < pieDatas.size(); i++) {
            PieData pieData = pieDatas.get(i);
            float percentage = pieData.getValue() * 100 / sumValue;
            pieData.setPercentage(percentage);
            pieData.setAngle(i == pieDatas.size() - 1 ? (360 - sumAngel) : percentage * 360 / 100);
            pieDatas.set(i, pieData);
            sumAngel += percentage * 360 / 100;
        }
    }
}
