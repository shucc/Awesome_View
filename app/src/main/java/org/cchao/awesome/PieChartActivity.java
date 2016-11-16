package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cchao.awesome.bean.PieData;
import org.cchao.awesome.widget.PieChartView;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    private final int[] colors = new int[]{0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000};
    private final int[] value = new int[] {345, 232, 66, 50, 656};

    private PieChartView pieChartViewOne;

    private ArrayList<PieData> pieDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        pieChartViewOne = (PieChartView) findViewById(R.id.pieChartView_one);

        pieDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PieData pieData = new PieData();
            pieData.setColor(colors[i]);
            pieData.setValue(value[i]);
            pieDatas.add(pieData);
        }
        pieChartViewOne.setData(pieDatas);
    }
}
