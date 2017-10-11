package com.prarui.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.prarui.common.conutils.NetWorkUtils;
import com.prarui.common.conutils.TagLog;
import com.prarui.common.conutils.ToastUtils;
import com.prarui.common.network.DownloadUtil;
import com.prarui.common.network.OkHttpManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://172.27.120.1/jsdms/index.php/Mobile/Sign/in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView viewById = (TextView) findViewById(R.id.hellow);
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

        map.put("username", "15708498417");
        map.put("password", "1234567");
        OkHttpManager.with().setPostRequest("sss", URL, "", map, new OkHttpManager.OnOkHttpResultCallbackListener() {
            @Override
            public void onError(String e) {
                ToastUtils.showToast(e);
            }

            @Override
            public void onResponseSucceed(String json) {
                viewById.setText(json);

            }
        });

//        DownloadUtil.with().download(image_url, "ourimage", new DownloadUtil.OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess() {
//                TagLog.d("下载成功");
//            }
//
//            @Override
//            public void onDownloading(int progress) {
//
//            }
//
//            @Override
//            public void onDownloadFailed() {
//                TagLog.d("下载失败");
//
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
