package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.cchao.awesome.widget.SideslipView;

public class SideslipActivity extends AppCompatActivity {

    private SideslipView mSLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sideslip);

        mSLView = (SideslipView) findViewById(R.id.sideslip);

        this.findViewById(R.id.text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SideslipActivity.this, "1111", Toast.LENGTH_SHORT).show();
                mSLView.close();
            }
        });
        this.findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SideslipActivity.this, "2222", Toast.LENGTH_SHORT).show();
                mSLView.close();
            }
        });
        this.findViewById(R.id.text3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SideslipActivity.this, "3333", Toast.LENGTH_SHORT).show();
                mSLView.close();
            }
        });
    }
}
