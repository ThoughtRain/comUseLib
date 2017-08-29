package com.prarui.common.conutils;


/**
 * Created by Prarui on 2017/7/7.
 */

public class UersData {
    public static String APP_VESION = "android";
    public static String APP_VISION_CODE = "1";
    public static String TOKEN = "token";
    public static String PHONE = "phone";

    public static void isRefresh(boolean refresh){
        SharePreferenceUtils.put("refresh",refresh);
    }

    public static boolean getRefresh(){
        return SharePreferenceUtils.getData("refresh");
    }

    public static void saveToken(String token) {
        SharePreferenceUtils.put(TOKEN, token);
    }


    public static String getToken() {
        return SharePreferenceUtils.getPrsonData("token");
    }


    public static void clearAll() {
        clearToken();
    }

    public static void clearToken() {
        /**
         * 清除token
         */
        SharePreferenceUtils.remove(TOKEN);
    }
}
