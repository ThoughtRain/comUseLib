package com.prarui.common.network;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;


import com.prarui.common.conutils.GsonUtils;
import com.prarui.common.conutils.NetWorkUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 在这个类中提供了网络去求的访问方式；
 * <p>post和get两种；
 * <p>
 * <p>
 * Created by Prarui on 2017/5/25.
 */

public class OkttpManager {
    private static OkHttpClient mOkHttpClient = null;
    public static void build() {
        if (null == mOkHttpClient) {
            mOkHttpClient = new OkHttpClient();
            mOkHttpClient = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * get请求方式
     **/
    public static void setGetRequest(Context context, String url, String token, HashMap<String, String> params, OnOkttpResultCallbackListener listener) {
        //API 14以下的处理
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        String get_url = url;
        if (params == null) {
            params = new HashMap<>();
        } else {
            //拼接url
            if (params != null && params.size() > 0) {
                int i = 0;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (i++ == 0) {
                        get_url = get_url + "?" + entry.getKey() + "=" + entry.getValue();
                    } else {
                        get_url = get_url + "&" + entry.getKey() + "=" + entry.getValue();
                    }
                }
            }
        }

        //请求体
        Request request;
        if (TextUtils.isEmpty(token)) {
            request = new Request.Builder()
                    .url(get_url)
                    .get()
                    .build();
        } else {
            request = new Request.Builder()
                    .url(get_url)
                    .get()
                    .header("token", token)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .build();
        }

        if (NetWorkUtils.isOpenNetwork(context)) {
            getNetWorkData(context, request, listener);
        } else {
            listener.onError("网络错误，请检查你的网络");
        }
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * post请求方式
     **/
    public static void setPostRequest(Context context, String url, String token, HashMap<String, String> params, final OnOkttpResultCallbackListener listener) {
        //API 14以下的处理
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (params == null) {
            params = new HashMap<>();
        }
        RequestBody requestBody = null;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;

            //判断值是否是空的
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }

//     把key和value添加到formbody中
            builder.add(key, value);
        }
        requestBody = builder.build();
        Request request;
        if (token == null) {
            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .header("token", token)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .post(requestBody)
                    .build();
        }

        if (NetWorkUtils.isOpenNetwork(context)) {
            getNetWorkData(context, request, listener);
        } else {
            listener.onError("网络错误，请检查你的网络");
        }
    }

    public static void setPutRequest(Context context, String url, String token, HashMap<String, String> params, final OnOkttpResultCallbackListener listener) {
        //API 14以下的处理
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (params == null) {
            params = new HashMap<>();
        }
        RequestBody requestBody = null;
        okhttp3.FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> map : params.entrySet()) {
            String key = map.getKey().toString();
            String value = null;

//     判断值是否是空的
            if (map.getValue() == null) {
                value = "";
            } else {
                value = map.getValue();
            }

//     把key和value添加到formbody中
            builder.add(key, value);
        }
        requestBody = builder.build();
        String json = GsonUtils.toJson(params);
        RequestBody requestBody1 = RequestBody.create(JSON, json);
        Request request;
        if (token == null) {
            request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .put(requestBody1)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .header("token", token)
                    .addHeader("Content-Type", "application/json")
                    .put(requestBody1)
                    .build();
        }

        Log.e("url", url);
        if (NetWorkUtils.isOpenNetwork(context)) {
            getNetWorkData(context, request, listener);
        } else {
            listener.onError("网络错误，请检查你的网络");
        }
    }

    //返回结果
    private static void getNetWorkData(final Context context, final Request request, final OnOkttpResultCallbackListener listener) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onError("请求数据失败");
                }

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (listener != null) {
                                listener.onResponSesucceed(response.body().string());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    //返回接口
    public interface OnOkttpResultCallbackListener {


        void onError(String e);

        void onResponSesucceed(String bean);

    }

}
