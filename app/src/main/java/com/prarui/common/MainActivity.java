package com.prarui.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.prarui.common.conutils.TagLog;
import com.prarui.common.network.DownloadUtil;
import com.prarui.common.network.OkHttpManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://172.27.35.1/jsdms/index.php/Mobile/Sign/in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView viewById = (TextView) findViewById(R.id.hellow);
        HashMap<String, String> map = new HashMap<>();

        map.put("username", "15708498417");
        map.put("password", "1234567");
//        OkHttpManager.with().setPostRequest(MainActivity.this, URL, "", map, new OkHttpManager.OnOkHttpResultCallbackListener() {
//            @Override
//            public void onError(String e) {
//
//            }
//
//            @Override
//            public void onResponseSucceed(String json) {
//                viewById.setText(json);
//
//            }
//        });

        DownloadUtil.with().download(image_url, "ourimage", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                TagLog.d("下载成功");
            }

            @Override
            public void onDownloading(int progress) {

            }

            @Override
            public void onDownloadFailed() {
                TagLog.d("下载失败");

            }
        });
    }

    public static String image_url = "http://pic39.nipic.com/20140312/18085061_092729513131_2.jpg";
}
