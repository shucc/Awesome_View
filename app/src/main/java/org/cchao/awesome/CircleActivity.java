package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cchao.awesome.widget.CircleProgressView;

public class CircleActivity extends AppCompatActivity {

    private CircleProgressView mFirstView;
    private CircleProgressView mSecondView;
    private CircleProgressView mThirdView;
    private CircleProgressView mFourView;

    private int mProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);

        mFirstView = (CircleProgressView) findViewById(R.id.activity_circle_first);
        mSecondView = (CircleProgressView) findViewById(R.id.activity_circle_second);
        mThirdView = (CircleProgressView) findViewById(R.id.activity_circle_third);
        mFourView = (CircleProgressView) findViewById(R.id.activity_circle_four);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress <= 100) {
                    mProgress++;
                    mFirstView.setProgress(mProgress);
                    mSecondView.setProgress(mProgress);
                    mThirdView.setProgress(mProgress);
                    mFourView.setProgress(mProgress);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
