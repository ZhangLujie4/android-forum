package com.zlj.createforum.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zlj.createforum.R;
//import com.zlj.zljapp.R;
//import com.zlj.zljapp.entity.MyUser;

//import cn.bmob.v3.exception.BmobException;
//import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   ForgetPasswordActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/11 12:45
 * 描述:    TODO
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_email;
    private Button btn_forget_pass;
    private Button btn_update_pass;
    private EditText et_new_pass, et_old_pass, et_pass_again;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }
    //初始化view
    private void initView() {
        et_new_pass = (EditText) findViewById(R.id.et_new_pass);
        et_old_pass = (EditText) findViewById(R.id.et_old_pass);
        et_pass_again = (EditText) findViewById(R.id.et_pass_again);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_update_pass = (Button) findViewById(R.id.btn_update_pass);
        btn_forget_pass = (Button) findViewById(R.id.btn_forget_pass);
        btn_forget_pass.setOnClickListener(this);
        btn_update_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_pass:
                //获取输入框的值
                String newPassword = et_new_pass.getText().toString().trim();
                String oldPassword = et_old_pass.getText().toString().trim();
                String rePassword = et_pass_again.getText().toString().trim();
                if (!TextUtils.isEmpty(newPassword) & !TextUtils.isEmpty(oldPassword)
                        & !TextUtils.isEmpty(rePassword)) {
                    if (newPassword.equals(rePassword)) {
//                        MyUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
//                            //当你登录过一次用户会缓存一个ObjectId,通过这个知道改哪一个
//                            @Override
//                            public void done(BmobException e) {
//                                if (e == null) {
//                                    Toast.makeText(ForgetPasswordActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                } else {
//                                    Toast.makeText(ForgetPasswordActivity.this, "修改失败:"+e.toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                    } else {
                        Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_forget_pass:
                //获取输入框邮箱
                final String email = et_email.getText().toString().trim();
                //判断邮箱是否为空
                if (!TextUtils.isEmpty(email)) {
                    //发送邮件
//                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null) {
//                                Toast.makeText(ForgetPasswordActivity.this, "重置请求成功，已发邮件至" + email, Toast.LENGTH_SHORT).show();
//                                finish();
//                            } else {
//                                Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
