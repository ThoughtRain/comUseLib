package com.prarui.common.conutils;

import android.util.Log;

/**
 * Created by Prarui on 2017/9/27.
 */

public class TagLog {
    private static String TAG = "prarui";
    public static void d(String... name) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < name.length; index++) {
            buffer.append(name[index] + "\n");
        }
        Log.d(TAG, buffer.toString());
    }
    public static void i(String... name) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < name.length; index++) {
            buffer.append(name[index] + "\n");
        }
        Log.i(TAG, buffer.toString());
    }
    public static void e(String... name) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < name.length; index++) {
            buffer.append(name[index] + "\n");
        }
        Log.e(TAG, buffer.toString());
    }
    public static void v(String... name) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < name.length; index++) {
            buffer.append(name[index] + "\n");
        }
        Log.v(TAG, buffer.toString());
    }
    public static void w(String... name) {
        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < name.length; index++) {
            buffer.append(name[index] + "\n");
        }
        Log.w(TAG, buffer.toString());
    }
}
