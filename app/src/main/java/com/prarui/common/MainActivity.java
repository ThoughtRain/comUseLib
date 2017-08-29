package com.prarui.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.prarui.common.conutils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.hellow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast("你好");
            }
        });
    }
}
