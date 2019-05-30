package com.zlj.createforum.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zlj.createforum.R;
import com.zlj.createforum.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   AboutActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/17 14:10
 * 描述:    关于软件
 */

public class AboutActivity extends BaseActivity {

    private ListView mListView;
    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //去掉阴影
        getSupportActionBar().setElevation(0);

        initView();

    }

    private void initView() {

        mListView = (ListView) findViewById(R.id.mListView);
        mList.add("应用名: " + getString(R.string.app_name));
        mList.add("版本号: " + UtilTools.getVersion(this));
        mList.add("发布时间: 2019-05-30 01:36");
        mList.add("描述: 寻文会友创作论坛");
        mList.add("功能描述: 具备PC网站的文章阅览功能和登录注册功能，更多功能可在网站上操作体验。");
        mList.add("网址: http://www.anhaozhang.com/");

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
        mListView.setAdapter(mAdapter);
    }


}
