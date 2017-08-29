package com.prarui.common.back;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prarui on 2017/6/22.
 */

public class PostInformationManager {
    public static final String TAG = "machine_tag";
    private static PostInformationManager machine = null;
    private OnPostInformationListener listener = null;
    //在这个地方储存每次发送数据的TAG；
    private List<String> listTag = new ArrayList<>();

    public static PostInformationManager getInstance() {
        if (machine == null) {
            machine = new PostInformationManager();
        }
        return machine;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (null != listener) {
                        listener.onPostInformation(msg.getData());
                    }
                    break;
            }
            return false;
        }
    });

    public void setPostInformationListener(OnPostInformationListener listener) {
        this.listener = listener;
    }

    /**
     * 取消发送信息
     **/
    public void cancel() {
        mHandler.removeMessages(1);
    }

    public void sentData(Bundle bundle, String tag) {
        listTag.add(tag);
        bundle.putString(TAG, tag);
        //设置传递参数。
        Message message = mHandler.obtainMessage(1);
        message.setData(bundle);
        mHandler.sendMessageDelayed(message, 0);
    }

    public interface OnPostInformationListener {
        void onPostInformation(Bundle bundle);
    }
}
