package com.zlj.createforum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.kymjs.rxvolley.client.HttpParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlj.createforum.MainActivity;
import com.zlj.createforum.R;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;
import com.zlj.createforum.view.CustomDialog;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.utils
 * 文件名:   LoginActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/10 17:39
 * 描述:    登录
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //注册按钮
    private Button btn_register;
    private EditText et_name, et_password;
    private Button btn_login;
    private CheckBox keep_password;

    //初始化一个dialog
    private CustomDialog dialog;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        initView();
    }

    private void initView() {
        keep_password = (CheckBox) findViewById(R.id.keep_password);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        dialog = new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, R.layout.dialog_loading,
                R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中状态
        boolean isCheck = ShareUtils.getBoolean(this, "keeppass", false);
        keep_password.setChecked(isCheck);
        if (isCheck) {
            //设置密码
            et_name.setText(ShareUtils.getString(this, "name", ""));
            et_password.setText(ShareUtils.getString(this, "password", ""));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                dialog.show();
                //获取输入框的值
                final String username = et_name.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)) {
                    //登录
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = StaticClass.REQUEST_PREFIX + "/api/common/user/login";

                            Map<String, String> map = new HashMap<>();
                            map.put("username", username);
                            map.put("password", password);

                            OkHttpClient client = new OkHttpClient();
                            Request request = new Request.Builder()
                                    .url(url)
                                    .addHeader("content-type", "application/json;charset=UTF-8")
                                    .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), JSON.toJSONString(map)))
                                    .build();
                            try {
                                Response response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                    dialog.dismiss();
                                    String str = response.body().string();
                                    L.i(str);
                                    JSONObject json = JSON.parseObject(str);
                                    boolean success = json.getBoolean("success");
                                    if (success) {
                                        JSONObject content = json.getJSONObject("content");
                                        ShareUtils.putString(LoginActivity.this, "uid", content.getString("uid"));
                                        ShareUtils.putString(LoginActivity.this, "token", content.getString("authorization"));
                                        ShareUtils.putString(LoginActivity.this, "nickname", content.getString("nickname"));
                                        ShareUtils.putString(LoginActivity.this, "avatar", content.getString("avatar"));
                                        ShareUtils.putString(LoginActivity.this, "username", content.getString("username"));
                                        ShareUtils.putString(LoginActivity.this, "tags", content.getString("tags"));
                                        ShareUtils.putString(LoginActivity.this, "avatar", content.getString("avatar"));
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Looper.prepare();
                                        Toast.makeText(LoginActivity.this, "登录失败："+json.get("content"),
                                                Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //输入了用户名密码，但不点击登录，而是退出
    @Override
    protected void onDestroy() {
        super.onDestroy();

        //保存
        ShareUtils.putBoolean(this, "keeppass", keep_password.isChecked());

        //是否记住密码
        if (keep_password.isChecked()) {
            //记住用户名和密码
            ShareUtils.putString(this, "name", et_name.getText().toString().trim());
            ShareUtils.putString(this, "password", et_password.getText().toString().trim());
        } else {
            ShareUtils.deleShare(this, "name");
            ShareUtils.deleShare(this, "password");
        }
    }
}
