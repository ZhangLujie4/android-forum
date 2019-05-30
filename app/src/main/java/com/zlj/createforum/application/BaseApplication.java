package com.zlj.createforum.application;

import android.app.Application;
/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.application
 * 文件名:   BaseApplication
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 1:54
 * 描述:    Application
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        //初始化Bugly
      //  CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        //初始化Bmob
//        Bmob.initialize(this, StaticClass.Bomb_APP_ID);

        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
      //  SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID +"="+StaticClass.VOICE_KEY);
        //L.i(SpeechConstant.APPID +"="+StaticClass.VOICE_KEY);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //SDKInitializer.initialize(getApplicationContext());
    }
}
