package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cchao.awesome.widget.RadarView;

import java.util.ArrayList;

public class RadarActivity extends AppCompatActivity {

    private RadarView radarView;

    private ArrayList<String> titles = new ArrayList<String>() {
        {
            add("a");
            add("b");
            add("c");
            add("d");
            add("e");
            add("f");
        }
    };
    private ArrayList<Integer> percentages = new ArrayList<Integer>() {
        {
            add(43);
            add(53);
            add(79);
            add(73);
            add(90);
            add(80);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        radarView = (RadarView) findViewById(R.id.radarview);
        radarView.setTitles(titles);
        radarView.setPercentages(percentages);
    }
}
