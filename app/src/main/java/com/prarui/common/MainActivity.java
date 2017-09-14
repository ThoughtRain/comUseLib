package com.prarui.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prarui.common.network.OkttpManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
public static final String URL="http://172.27.35.1/jsdms/index.php/Mobile/Sign/in";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView viewById = (TextView) findViewById(R.id.hellow);
        HashMap<String,String> map=new HashMap<>();
        map.put("username","15708498417");
        map.put("password","1234567");
        OkttpManager.setPostRequest(MainActivity.this, URL, "", map, new OkttpManager.OnOkttpResultCallbackListener() {
            @Override
            public void onError(String e) {

            }

            @Override
            public void onResponSesucceed(String bean) {
              viewById.setText(bean);
            }
        });
    }
}
