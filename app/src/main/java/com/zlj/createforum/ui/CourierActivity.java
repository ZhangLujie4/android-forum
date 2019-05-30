package com.zlj.createforum.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.zlj.createforum.R;
import com.zlj.createforum.adapter.CourierAdapter;
import com.zlj.createforum.entity.CourierData;
import com.zlj.createforum.utils.L;
import com.zlj.createforum.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.ui
 * 文件名:   CourierActivity
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/12 20:38
 * 描述:    快递查询
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_courier;
    private ListView mListView;
    private List<CourierData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_courier = (Button) findViewById(R.id.btn_courier);
        mListView = (ListView) findViewById(R.id.mListView);
        btn_courier.setOnClickListener(this);
        //去掉分割线
        mListView.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_courier:
                //获取到值
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();
                //拼接url
                String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.COURIER_KEY
                        +"&com=" + name +"&no=" + number;

                //值不能为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(number)) {
                    //请求数据json,解析
//                    RxVolley.get(url, new HttpCallback() {
//                        @Override
//                        public void onSuccess(String t) {
//                            //Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
//                            L.i("Json:"+t);
//                            //解析json
//                            parsingJson(t);
//                        }
//                    });
                } else {
                    Toast.makeText(this, R.string.edit_view_null_error, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //解析数据
    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                CourierData data = new CourierData();
                data.setDatetime(json.getString("datetime"));
                data.setRemark(json.getString("remark"));
                data.setZone(json.getString("zone"));
                mList.add(data);
            }
            //倒序
            Collections.reverse(mList);
            CourierAdapter adapter = new CourierAdapter(this, mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
