package com.zlj.createforum.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zlj.createforum.R;
import com.zlj.createforum.adapter.WeChatAdapter;
import com.zlj.createforum.entity.WeChatData;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;
import com.zlj.createforum.utils.UtilTools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   WebViewActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 16:56
 * 描述:    微信详情页
 */

public class ArticleActivity extends BaseActivity implements View.OnClickListener {

    //textView
    private TextView mWebView;
    //进度条
    private ProgressBar mProgressBar;

    private TextView nickName;
    private Handler handler = null;
    private Handler clickHandler = null;
    private Handler likeHandler = null;
    private Handler commentHandler = null;
    private Handler updateHandler = null;
    private Button button;
    private CircleImageView avatar;
    private String aid;
    private String uid;
    private TextView likeText;
    private ImageView like;
    private String token;
    private EditText editText;
    private Button button2;
    private String username;
    private String nickname;
    private WeChatAdapter adapter;

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        initView();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.mListView);
        mWebView = (TextView) findViewById(R.id.mWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        avatar = (CircleImageView) findViewById(R.id.profile_image);
        nickName = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);
        like = (ImageView) findViewById(R.id.like);
        likeText = (TextView) findViewById(R.id.likeText);
        button2 = (Button) findViewById(R.id.button3);
        editText = (EditText) findViewById(R.id.editText);
        button2.setOnClickListener(this);
        handler = new Handler() {
            public void handleMessage(Message msg) {
                parsingJson((String) msg.obj);
                mProgressBar.setVisibility(View.GONE);
            }
        };
        commentHandler =  new Handler() {
            public void handleMessage(Message msg) {
                parsingComment((String) msg.obj);
            }
        };
        updateHandler = new Handler() {
            public void handleMessage(Message msg) {
                WeChatData weChatData = (WeChatData) msg.obj;
                mList.add(0, weChatData);
                editText.setText("");
                new WeChatAdapter(getApplicationContext(), mList);
                mListView.setAdapter(adapter);
                setHeight();
            }
        };
        clickHandler = new Handler() {
            public void handleMessage(Message msg) {
                L.i(button.getText().toString());
                if (button.getText().toString().equals("+关注")) {
                    button.setText("已关注");
                    button.setBackgroundResource(R.drawable.button_followed);
                } else {
                    button.setText("+关注");
                    button.setBackgroundResource(R.drawable.button_follow);
                }
            }
        };

        likeHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (likeText.getText().toString().equals("点赞")) {
                    likeText.setText("已点赞");
                    like.setImageResource(R.drawable.like);
                } else {
                    likeText.setText("点赞");
                    like.setImageResource(R.drawable.unlike);
                }
            }
        };
        button.setOnClickListener(this);
        like.setOnClickListener(this);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        aid = intent.getStringExtra("url");
        token = ShareUtils.getString(this, "token", "");
        nickname = ShareUtils.getString(this, "nickname", "");
        username = ShareUtils.getString(this, "username", "");

        //设置标题
        getSupportActionBar().setTitle(title);

        getArticle();

        getComment();
        //加载网页
        //支持js
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        //支持缩放
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        //接口
//        mWebView.setWebChromeClient(new WebViewClient());
//        //加载网页
//        mWebView.loadUrl(url);
//        //本地加载
//        mWebView.setWebViewClient(new android.webkit.WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }

    private void getArticle() {
        //解析接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = StaticClass.REQUEST_PREFIX + "/api/common/article/detail?id=" + aid;

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Authorization","Bearer " + token)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        L.i("结果是" + str);
                        Message message = Message.obtain();
                        message.obj = str;
                        handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void getComment() {
        //解析接口
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = StaticClass.REQUEST_PREFIX + "/api/common/comment/list?id=" + aid + "&page=1&size=100";

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String str = response.body().string();
                        L.i("结果是" + str);
                        Message message = Message.obtain();
                        message.obj = str;
                        commentHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void parsingComment(String str) {
        try {
            JSONObject object = JSON.parseObject(str);
            JSONObject json = object.getJSONObject("content");
            JSONArray array = json.getJSONArray("comments");
            for (int i = 0; i < array.size(); i++) {
                com.alibaba.fastjson.JSONObject item = (JSONObject) array.get(i);
                WeChatData data = new WeChatData();
                data.setImgUrl(item.getString("id"));
                data.setNewsUrl(item.getString("content"));
                data.setSource(item.getString("uid"));
                data.setTitle(item.getString("nickName"));
                mList.add(data);
            }
            adapter = new WeChatAdapter(this, mList);
            mListView.setAdapter(adapter);
            setHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHeight() {
        int height = 0;
        int count = adapter.getCount();
        for(int i=0;i<count;i++){
            View temp = adapter.getView(i,null,mListView);
            temp.measure(0,0);
            height += temp.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = this.mListView.getLayoutParams();
        params.height = height;
        mListView.setLayoutParams(params);
    }

    private void parsingJson(String str) {
        try {
            JSONObject object = JSON.parseObject(str);
            JSONObject json = object.getJSONObject("content");
            String avatar_url = json.getString("avatar");
            if (avatar_url == null || avatar_url.length() <= 0) {
                avatar_url = StaticClass.default_avatar;
            }
            String html = json.getString("content");
            String nickname = json.getString("nickname");
            if (nickname == null || nickname.length() <= 0) {
                nickname = json.getString("username");
            }
            if (json.getBoolean("is_self")) {
                button.setVisibility(View.INVISIBLE);
            } else {
                button.setVisibility(View.VISIBLE);
                if (json.getBoolean("follow")) {
                    button.setText("已关注");
                    button.setBackgroundResource(R.drawable.button_followed);
                } else {
                    button.setText("+关注");
                    button.setBackgroundResource(R.drawable.button_follow);
                }
            }
            if (json.getBoolean("like")) {
                likeText.setText("已点赞");
                like.setImageResource(R.drawable.like);
            } else {
                likeText.setText("点赞");
                like.setImageResource(R.drawable.unlike);
            }
            uid = json.getString("author");
            nickName.setText(nickname);
            html = html.replaceAll("<img.*?>", "");
            mWebView.setText(Html.fromHtml(html));
            UtilTools.getImageFromUrl(avatar_url, avatar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //解析接口
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = StaticClass.REQUEST_PREFIX + "/api/user/follow?id=" + uid;

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .addHeader("Authorization","Bearer " + token)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                Message message = Message.obtain();
                                message.obj = null;
                                clickHandler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case R.id.like:
                //解析接口
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = StaticClass.REQUEST_PREFIX + "/api/user/article/like?id=" + aid;

                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .addHeader("Authorization","Bearer " + token)
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            if (response.isSuccessful()) {
                                Message message = Message.obtain();
                                message.obj = null;
                                likeHandler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case R.id.button3:
                final String edit = editText.getText().toString();
                if (edit != null && edit.length() > 0) {
                    //解析接口
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = StaticClass.REQUEST_PREFIX + "/api/user/comment/add";

                            Map<String, Object> map = new HashMap<>();
                            map.put("content", edit);
                            map.put("rid", 0);
                            map.put("aid", aid);

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .addHeader("Authorization","Bearer " + token)
                                    .addHeader("content-type", "application/json;charset=UTF-8")
                                    .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), JSON.toJSONString(map)))
                                    .build();

                            try {
                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    WeChatData weChatData = new WeChatData();
                                    weChatData.setTitle(nickname.length() > 0 ? nickname : username);
                                    weChatData.setNewsUrl(edit);

                                    Message message = Message.obtain();
                                    message.obj = weChatData;
                                    updateHandler.sendMessage(message);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
        }
    }
}
