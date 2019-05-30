package com.zlj.createforum.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlj.createforum.R;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.StaticClass;

import org.json.JSONObject;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   PhoneActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/12 23:50
 * 描述:    归属地查询
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_number;
    private ImageView img_company;
    private TextView tv_result;
    private Button btn_1, btn_2, btn_3, btn_del;
    private Button btn_4, btn_5, btn_6, btn_0;
    private Button btn_7, btn_8, btn_9, btn_query;

    //标记位，用来更新判断
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        et_number = (EditText) findViewById(R.id.et_number);
        img_company = (ImageView) findViewById(R.id.img_company);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_query.setOnClickListener(this);

        //长按事件
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                et_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        //获取到输入框的内容
        String str = et_number.getText().toString();

        switch (view.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if (flag) {
                    flag = false;
                    str = "";
                    et_number.setText("");
                }
                et_number.setText(str + ((Button)view).getText());
                et_number.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    //每次结尾减去1
                    et_number.setText(str.substring(0, str.length()-1));
                    //移动光标
                    et_number.setSelection(str.length() - 1);
                }
                break;
            case R.id.btn_query:
                if (!TextUtils.isEmpty(str)) {
                    getPhone(str);
                }
                break;
        }

    }

    //获得归属地
    private void getPhone(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + str
                + "&key=" + StaticClass.PHONE_KEY;
//        RxVolley.get(url, new HttpCallback() {
//            @Override
//            public void onSuccess(String t) {
//                L.i("phone:" + t);
//                parsingJson(t);
//            }
//        });
    }

    //解析Json

    /**
     * "province":"浙江",
     * "city":"杭州",
     * "areacode":"0571",
     * "zip":"310000",
     * "company":"中国移动",
     " "card":""
     * @param t
     */
    private void  parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject json = jsonObject.getJSONObject("result");
            String province = json.getString("province");
            String city = json.getString("city");
            String areacode = json.getString("areacode");
            String zip = json.getString("zip");
            String company = json.getString("company");
            String card = json.getString("card");

            tv_result.setText("");

            tv_result.append("归属地:" + province + city + "\n");
            tv_result.append("区号:" + areacode + "\n");
            tv_result.append("邮编:" + zip + "\n");
            tv_result.append("运营商:" + company + "\n");
            tv_result.append("类型:" + card);
            //图片显示
            switch (company) {
                case "移动":
                    img_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    img_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    img_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
