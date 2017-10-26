package com.prarui.common.conutils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Prarui on 2017/8/21.
 */

public class TextUtils {
    /**
     * 把图片转换成流并压缩
     */
    public static String disposeImage(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            /*
             * 读取和压缩图片资源 并将其保存在 ByteArrayOutputStream对象中
			 */
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
             String imgBase64 = new String(Base64.encode(outputStream.toByteArray(), Base64.DEFAULT));
            return imgBase64;
        } catch (Exception e) {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return "";
        }
    }

}
