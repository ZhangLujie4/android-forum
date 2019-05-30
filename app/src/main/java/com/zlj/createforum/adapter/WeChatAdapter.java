package com.zlj.createforum.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlj.createforum.R;
import com.zlj.createforum.entity.WeChatData;
import com.zlj.createforum.utils.L;

import java.util.List;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.adapter
 * 文件名:   WechatAdapter
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 14:28
 * 描述:    微信adapter
 */

public class WeChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private WeChatData data;
    private List<WeChatData> weChatDataList;
    private int width, height;
    private WindowManager wm;

    public WeChatAdapter (Context context, List<WeChatData> weChatDataList) {
        this.context = context;
        this.weChatDataList = weChatDataList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        L.i("Width:" + width + "Height:" + height);
    }

    @Override
    public int getCount() {
        return weChatDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return weChatDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.wechat_item, null);
//            viewHolder.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            viewHolder.tv_source = (TextView) view.findViewById(R.id.tv_source);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        data = weChatDataList.get(i);
        viewHolder.tv_source.setText(data.getNewsUrl());
        viewHolder.tv_title.setText(data.getTitle());
//        if (!TextUtils.isEmpty(data.getImgUrl())) {
//            PicassoUtils.loadImageViewDefaultSize(context, data.getImgUrl(), R.drawable.pig,
//                    width/3, 200, viewHolder.iv_img);
//        }
        return view;
    }
    class ViewHolder {
//        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
