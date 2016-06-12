package org.cchao.awesome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.cchao.awesome.widget.SwitchButton;

public class SwitchActivity extends AppCompatActivity {

    private SwitchButton switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);

        switchButton = (SwitchButton) findViewById(R.id.activity_switchbutton);

        switchButton.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onChange(boolean isChecked) {
                LogUtil.i(isChecked ? "选中" : "取消选中");
            }
        });
    }
}
