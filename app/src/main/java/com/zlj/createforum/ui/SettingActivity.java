package com.zlj.createforum.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.zlj.createforum.R;
import com.zlj.createforum.service.MessageService;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   SettingActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 17:17
 * 描述:    设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

//    //语音播报
//    private Switch sw_speak;
//    //智能短信提醒
//    private Switch sw_message;
//
//    private LinearLayout ll_update;
//    private TextView tv_version;
//    //扫一扫
//    private LinearLayout ll_scan;
//    //扫描结果
//    private TextView tv_scan_result;
//    private LinearLayout ll_qr_code;
//    //定位
//    private LinearLayout ll_my_location;

    private LinearLayout ll_about;

    private int versionCode;
    private String versionName;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
//        sw_speak = (Switch) findViewById(R.id.sw_speak);
//        sw_speak.setOnClickListener(this);
//        sw_message = (Switch) findViewById(R.id.sw_message);
//        sw_message.setOnClickListener(this);
//        ll_update = (LinearLayout) findViewById(R.id.ll_update);
//        ll_update.setOnClickListener(this);
//        tv_version = (TextView) findViewById(R.id.tv_version);
//        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
//        ll_scan.setOnClickListener(this);
//        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
//        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
//        ll_qr_code.setOnClickListener(this);
//        ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
//        ll_my_location.setOnClickListener(this);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_about.setOnClickListener(this);

        boolean isSpeak = ShareUtils.getBoolean(this, "isSpeak", false);
//        sw_speak.setChecked(isSpeak);

        boolean isNotice = ShareUtils.getBoolean(this, "isNotice", false);
//        sw_message.setChecked(isNotice);

//        try {
//            getVersionNameCode();
//            tv_version.setText("检测版本:" + versionName);
//        } catch (PackageManager.NameNotFoundException e) {
//            tv_version.setText("检测版本");
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.sw_speak:
//                //切换相反
//                sw_speak.setSelected(!sw_speak.isSelected());
//                //保存状态
//                ShareUtils.putBoolean(this, "isSpeak", sw_speak.isChecked());
//                break;
//            case R.id.sw_message:
//                sw_message.setSelected(!sw_message.isSelected());
//                ShareUtils.putBoolean(this, "isNotice", sw_message.isChecked());
//                if (sw_message.isChecked()) {
//                    startService(new Intent(this, MessageService.class));
//                } else {
//                    stopService(new Intent(this, MessageService.class));
//                }
//                break;
//            case R.id.ll_update:
//                /**
//                 * 请求服务器配置文件，拿到code
//                 * 比较
//                 * dialog提示
//                 * 跳转到更新界面，并传递url
//                 *
//                 */
////                RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
////                    @Override
////                    public void onSuccess(String t) {
////                        L.i(t);
////                        parsingJson(t);
////                    }
////
////                    @Override
////                    public void onFailure(VolleyError error) {
////                        L.e("连不上url");
////                    }
////                });
//                break;
//            case R.id.ll_scan:
//                L.i("scan out");
//                //Intent openCameraIntent = new Intent(this, CaptureActivity.class);
//                //startActivityForResult(openCameraIntent, 0);
//                break;
//            case R.id.ll_qr_code:
//                startActivity(new Intent(this, QrCodeActivity.class));
//                break;
//            case R.id.ll_my_location:
//                L.i("map out");
//                //startActivity(new Intent(this, LocationActivity.class));
//                break;
            case R.id.ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
    }

    //解析json
    private void parsingJson(String t) {
        try {
            JSONObject json = new JSONObject(t);
            int code = json.getInt("versionCode");
            if (code > versionCode) {
                url = json.getString("url");
                showUpdateDialog(json.getString("content"));
            } else {
                Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //弹出升级提示
    private void showUpdateDialog(String content) {
        new AlertDialog.Builder(this).setTitle("有新版本啦!").setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //什么都不做，也会执行dismis方法
            }
        }).show();
    }

    //获取版本号code
    private void getVersionNameCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionName = info.versionName;
        versionCode = info.versionCode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
//            tv_scan_result.setText(scanResult);
        }
    }
}
