package com.zlj.createforum.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zlj.createforum.MainActivity;
import com.zlj.createforum.R;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.ShareUtils;
import com.zlj.createforum.utils.StaticClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   RegisterActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/10 18:11
 * 描述:    注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_user, et_age, et_desc, et_pass, et_repass, et_email;
    private RadioGroup mRadioGroup;
    private Button btn_register;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_user = (EditText) findViewById(R.id.et_user);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_repass = (EditText) findViewById(R.id.et_repass);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                //获取到输入框的值
                final String name = et_user.getText().toString().trim();
                final String pass = et_pass.getText().toString().trim();
                String repass = et_repass.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name)
                        & !TextUtils.isEmpty(pass)
                        & !TextUtils.isEmpty(repass)) {
                    //判断两次输入密码是否一致
                    if (pass.equals(repass)) {
                        //注册
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = StaticClass.REQUEST_PREFIX + "/api/common/user/register";

                                Map<String, String> map = new HashMap<>();
                                map.put("username", name);
                                map.put("password", pass);

                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
                                        .url(url)
                                        .addHeader("content-type", "application/json;charset=UTF-8")
                                        .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), JSON.toJSONString(map)))
                                        .build();
                                try {
                                    Response response = client.newCall(request).execute();
                                    if (response.isSuccessful()) {
                                        String str = response.body().string();
                                        L.i(str);
                                        JSONObject json = JSON.parseObject(str);
                                        boolean success = json.getBoolean("success");
                                        Looper.prepare();
                                        if (success) {
                                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "注册失败:"+json.get("content"),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        Looper.loop();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();
                    } else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //跳出框,自动消失
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
