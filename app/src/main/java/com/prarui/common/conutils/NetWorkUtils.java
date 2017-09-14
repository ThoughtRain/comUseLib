package com.prarui.common.conutils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * NetConUtils(是否网络连接工具类)
 *
 * Created by prarui on 2017/5/26.
 */

public class NetWorkUtils
{
    /**
     * 检查网络连接情况
     * @param context
     * @return
     */
   public static boolean isOpenNetwork(Context context)
    {
        ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}
