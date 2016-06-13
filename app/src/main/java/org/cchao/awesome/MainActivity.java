package org.cchao.awesome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.activity_main_btn_circle_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CircleActivity.class));
            }
        });
        this.findViewById(R.id.activity_main_btn_sideslip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SideslipActivity.class));
            }
        });
        this.findViewById(R.id.activity_main_btn_horizontal_progress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HorizontalActivity.class));
            }
        });
        this.findViewById(R.id.activity_main_btn_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SwitchActivity.class));
            }
        });
        this.findViewById(R.id.activity_main_btn_codes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CodesActivity.class));
            }
        });
    }
}
