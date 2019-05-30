package com.zlj.createforum.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.zlj.createforum.R;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   QrCodeActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/17 1:32
 * 描述:    生成二维码
 */

public class QrCodeActivity extends BaseActivity {

    private ImageView iv_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        
        initView();
    }

    private void initView() {
//        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
//        //屏幕的宽
//        int width = getResources().getDisplayMetrics().widthPixels;
//
//        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是zjuter", width/2, width/2,
//                        BitmapFactory.decodeResource(getResources(), R.drawable.pig));
//        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
