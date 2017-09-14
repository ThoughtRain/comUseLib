package com.prarui.common;

import android.content.Context;

import com.prarui.common.conutils.SharePreferenceUtils;
import com.prarui.common.conutils.SystemUtils;
import com.prarui.common.conutils.ToastUtils;
import com.prarui.common.network.OkttpManager;

/**
 * Created by Prarui on 2017/8/29.
 * 初始化数据
 */

public class CrotonCake {
    public static void initData(Context context) {
        SharePreferenceUtils.buid(context);
        SystemUtils.build(context);
        ToastUtils.buid(context);
        OkttpManager.build();
    }
}
