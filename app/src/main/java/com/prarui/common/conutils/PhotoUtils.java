package com.prarui.common.conutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Prarui on 2017/8/21.
 */

public class PhotoUtils {
    /**
     * 请求码
     */
    private Context context;
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int PERMISSIONS_REQUEST_FRONT = 4;

    public int bitmapWidth = 340;
    public int bitmapHeight = 340;

    private int viewId;

    public void setBitmapSize(int bitmapWidth,int bitmapHeight) {
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
    }

    /**
     * 头像名称
     */
    private static final String IMAGE_FILE_NAME = "image.jpg";

    public PhotoUtils(final Context context) {
        this.context = context;
    }

    public void showVersion(int id) {
        viewId = id;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context
                    ,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_FRONT);
            } else {
                showDialog();
            }
        } else {
            showDialog();
        }
    }

    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case IMAGE_REQUEST_CODE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        ((Activity) context).startActivityForResult(openAlbumIntent, IMAGE_REQUEST_CODE);
                        break;
                    case CAMERA_REQUEST_CODE: // 拍照
                        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 判断存储卡是否可以用，可用进行存储
                        String state = Environment.getExternalStorageState();
                        if (state.equals(Environment.MEDIA_MOUNTED)) {
                            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File file = new File(path, IMAGE_FILE_NAME);
                            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        }
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        ((Activity) context).startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, OnPhotoSelectListener listener) {
        if (resultCode != Activity.RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;

                case CAMERA_REQUEST_CODE:
                    // 判断存储卡是否可以用，可用进行存储
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                        File tempFile = new File(path, IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                        // 找的路径；

                    } else {
                        Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(listener);
                    }
                    break;
            }
        }

    }

    private Uri imageUri;

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (uri == null) {
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            String url = MantiSelectImageUtils.getPath(context, uri);
            uri = Uri.fromFile(new File(url));
            intent.setDataAndType(uri, "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }

        Bitmap bitmap = null;
        String str = "";

        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            str = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
            imageUri = Uri.parse(str);
        } catch (Exception exception) {
            imageUri = uri;
            getImageToView(listener);
            return;
        }

        intent.setDataAndType(imageUri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", bitmapWidth);
        intent.putExtra("outputY", bitmapHeight);
        intent.putExtra("return-data", false);

        intent.putExtra("output", imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

        ((Activity) context).startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */

    private void getImageToView(OnPhotoSelectListener listener) {
        try {
            if (listener != null) {
                listener.photoSelect(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri)),viewId);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private OnPhotoSelectListener listener;

    public interface OnPhotoSelectListener {
        void photoSelect(Bitmap bitmap, int viewId);
    }
}
