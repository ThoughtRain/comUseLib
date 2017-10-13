package com.prarui.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prarui.common.conutils.NetWorkUtils;
import com.prarui.common.conutils.TagLog;
import com.prarui.common.conutils.ToastUtils;
import com.prarui.common.network.DownloadUtil;
import com.prarui.common.network.OkHttpManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://172.27.120.1/jsdms/index.php/Mobile/Sign/in";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.hellow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToast("这个是id：" + view.getId());
            }
        });
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showToast("ssssssss");
//            }
//        });
        HashMap<String, String> map = new HashMap<>();
//        viewById.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (NetWorkUtils.isOpenNetwork()) {
//                    ToastUtils.showToast("网络连接正常");
//                } else {
//                    ToastUtils.showToast("网络连接异常");
//                }
//
//            }
//        });

//        map.put("username", "15708498417");
//        map.put("password", "1234567");
//        OkHttpManager.with().setPostRequest("sss", URL, "", map, new OkHttpManager.OnOkHttpResultCallbackListener() {
//            @Override
//            public void onError(String e) {
//                ToastUtils.showToast(e);
//            }
//
//            @Override
//            public void onResponseSucceed(String json) {
//                viewById.setText(json);
//
//            }
//        });
//        String ud = "http://192.168.1.5:1212/login/paramTest.do";
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("name", "wangrui");
//        hashMap.put("age", "122");
//        OkHttpManager.with().setPostRequest("post", ud, "", hashMap, new OkHttpManager.OnOkHttpResultCallbackListener() {
//            @Override
//            public void onError(String e) {
//                ToastUtils.showToast(e);
//            }
//
//            @Override
//            public void onResponseSucceed(String json) {
//                viewById.setText(json);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpManager.with().cancelNetWorkByTag("ss");
    }

    public static String image_url = "http://pic39.nipic.com/20140312/18085061_092729513131_2.jpg";
}
