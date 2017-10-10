package com.prarui.common.network;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.prarui.common.conutils.TagLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by prarui on 2017/9/29.
 * app升级代码
 */

public class DownloadUtil {
    //直接切换回主线程；
    private boolean isFinish = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (null != listener) {
                        listener.onDownloadFailed();
                    }
                    break;
                case 1:
                    if (null != listener) {
                        listener.onDownloading(message.arg1);
                    }
                    break;
                case 2:
                    if (null != listener) {
                        listener.onDownloadSuccess();
                    }

                    break;

            }
            return false;
        }
    });
    private static DownloadUtil downloadUtil;

    public static DownloadUtil with() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }



    private OnDownloadListener listener;
    /**
     * @param url     下载连接
     * @param saveDir 储存下载文件的SDCard目录
     */

    public DownloadUtil download(final String url, final String saveDir, OnDownloadListener listener) {
        this.listener = listener;
        Request request = new Request.Builder().url(url).build();
        OkHttpManager.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
                // 下载失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        TagLog.d("开始" + sum);
                        // 下载中
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = progress;
                        handler.sendMessage(message);

                    }
                    isFinish = true;
                    TagLog.d("完成");
                    // 下载完成
                    fos.flush();
                } catch (Exception e) {
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                    isFinish = false;
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
                if (isFinish) {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }

            }

        });
        return this;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();

        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    // 在手机上打开文件
    public void openFile(Context mContext, String pakageName, String url) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        // 设定intent的file与MimeType，安装文件
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                        + "/" + pakageName + "/" + getNameFromUrl(url))),
                "application/vnd.android.package-archive");
        TagLog.d(Environment.getExternalStorageDirectory()
                + "/" + pakageName + "/" + getNameFromUrl(url));
        mContext.startActivity(intent);

    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}
