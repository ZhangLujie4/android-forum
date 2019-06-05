package com.zlj.createforum.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zlj.createforum.MainActivity;
import com.zlj.createforum.R;
import com.zlj.createforum.adapter.WeChatAdapter;
import com.zlj.createforum.entity.WeChatData;
import com.zlj.createforum.ui.ArticleActivity;
import com.zlj.createforum.ui.LoginActivity;
import com.zlj.createforum.ui.WebViewActivity;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.fragment
 * 文件名:   WechatFragment
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 15:35
 * 描述:    微信精选
 */

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    private Handler handler = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                List<WeChatData> list = (List<WeChatData>) msg.obj;
                WeChatAdapter adapter = new WeChatAdapter(getActivity(), list);
                mListView.setAdapter(adapter);
            }
        };
        findView(view);
        return view;
    }

    //初始化
    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        SharedPreferences sp = this.getActivity().getSharedPreferences(ShareUtils.NAME, Context.MODE_PRIVATE);
        final String token = sp.getString("token", "");

        //解析接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = StaticClass.REQUEST_PREFIX + "/api/user/follower/article";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization","Bearer " + token)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        L.i(str);
                        parsingJson(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
//        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=50";
//        RxVolley.get(url, new HttpCallback() {
//            @Override
//            public void onSuccess(String t) {
//                L.i("wechat:"+t);
//                parsingJson(t);
//            }
//        });

        //点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                L.i("position:"+i);
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("key", "values");
//                intent.putExtras(bundle);
                intent.putExtra("title", mList.get(i).getTitle());
                intent.putExtra("url", mList.get(i).getImgUrl());
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject json = JSON.parseObject(t);
            JSONArray list = json.getJSONArray("content");
            for (int i = 0; i < list.size(); i++) {
                JSONObject item = (JSONObject) list.get(i);
                WeChatData data = new WeChatData();
                data.setImgUrl(item.getString("aid"));
                data.setNewsUrl(item.getString("content").substring(0, 200) + "...");
                data.setSource(item.getString("id"));
                data.setTitle(item.getString("title"));
                mList.add(data);
            }
            L.i(mList.toString());
            Message message = Message.obtain();
            message.obj = mList;
            handler.sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//        try {
//            JSONObject object = new JSONObject(t);
//            JSONObject result = object.getJSONObject("result");
//            JSONArray list = result.getJSONArray("list");
//            for (int i=0; i<list.length(); i++) {
//                JSONObject json = list.getJSONObject(i);
//                WeChatData data = new WeChatData();
//                data.setImgUrl(json.getString("firstImg"));
//                data.setNewsUrl(json.getString("url"));
//                data.setSource(json.getString("source"));
//                data.setTitle(json.getString("title"));
//                mList.add(data);
//            }
//            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
//            mListView.setAdapter(adapter);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
}
