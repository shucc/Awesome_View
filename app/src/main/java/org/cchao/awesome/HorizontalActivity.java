package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cchao.awesome.widget.HorizontalProgressView;

public class HorizontalActivity extends AppCompatActivity {

    private HorizontalProgressView horizontalProgressViewOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal);

        horizontalProgressViewOne = (HorizontalProgressView) findViewById(R.id.activity_horizontal_one);

        horizontalProgressViewOne.setProgress(30);
    }
}
