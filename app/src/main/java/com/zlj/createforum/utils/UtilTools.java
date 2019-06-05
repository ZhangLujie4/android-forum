package com.zlj.createforum.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.utils
 * 文件名:   UtilTools
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 13:00
 * 描述:    工具统一类
 */

public class UtilTools {

    //设置字体
    public static void setFont(Context mContext, TextView textView) {
        Typeface fontType = Typeface.createFromAsset(mContext.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    public static void putImageToShare(Context myContent, ImageView profile_image) {
        //保存
        BitmapDrawable drawable = (BitmapDrawable) profile_image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //1.将bitmap压缩成字节数组输出流
        ByteArrayOutputStream byStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
        //2.利用Base64将字节流转化成string
        byte[] byteArray = byStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //3.将string保存在shareUtils
        ShareUtils.putString(myContent, "image_title", imgString);
    }

    public static void getImageToShare(Context myContent, ImageView profile_image) {
        //1.拿到string
        String imgString = ShareUtils.getString(myContent, "image_title", "");
        if (!imgString.equals("")) {
            //2.利用base64把string转换成字节流
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
            //3.生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            profile_image.setImageBitmap(bitmap);
        }
    }

    public static void getImageFromUrl(String url, final ImageView profile_image) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url).build();
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                byte[] bitmap = (byte[]) msg.obj;
                Bitmap bm = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
                profile_image.setImageBitmap(bm);
            }
        };
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bitmap = response.body().bytes();
                Message message = handler.obtainMessage();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });
    }

    //获取版本号
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "未知";
        }
    }

}
