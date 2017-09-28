package com.prarui.common.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


/**
 * Created by Prarui on 2017/9/19.
 */

public class CheckPermissionUtils {

    public static class Builder {
        private Context context;
        private OnRequestPermissionListener listener;
        private AlertDialog.Builder builder;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder checkPermission(final String permission, final int permissions_request_code, final OnRequestPermissionListener listener) {
            this.listener = listener;

            if (Build.VERSION.SDK_INT >= 23) {
                builder = new AlertDialog.Builder(context);
                if (ContextCompat.checkSelfPermission(context,
                        permission) != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (null != listener) {
                                    listener.requestFailed();
                                }
                            }
                        }).setMessage("否申需要用户权限申请，是否申请？？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, permissions_request_code);
                            }
                        }).create();
                        builder.show();
                        return this;
                    }
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, permissions_request_code);
                    return this;
                } else {
                    if (null != listener) {
                        listener.requestSucceed(permission, permissions_request_code);
                    }
                }
            } else {
                if (null != listener) {
                    listener.requestSucceed(permission, permissions_request_code);
                }
            }
            return this;
        }

        public Builder onRequestPermissionsResult(int permissions_request_code, String[] permissions, int[] grantResults) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (null != listener) {
                    listener.requestSucceed(permissions[0], permissions_request_code);
                }
            } else {
                if (null != listener) {
                    listener.requestFailed();
                }


            }
            return this;
        }


    }

    public static void startAppSettings(Context context) {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public interface OnRequestPermissionListener {
        void requestSucceed(String permission, int permissions_request_code);

        void requestFailed();

    }

}

