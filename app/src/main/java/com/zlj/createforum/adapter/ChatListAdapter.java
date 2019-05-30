package com.zlj.createforum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zlj.createforum.R;
import com.zlj.createforum.entity.ChatData;

import java.util.List;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.adapter
 * 文件名:   ChatListAdapter
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 10:13
 * 描述:    对话适配器
 */

public class ChatListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context myContext;
    private LayoutInflater inflater;
    private List<ChatData> chatDataList;
    private ChatData chatData;

    public ChatListAdapter(Context myContext, List<ChatData> chatDataList) {
        this.myContext = myContext;
        this.chatDataList = chatDataList;
        //获取系统服务
        inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderLeftText leftText = null;
        ViewHolderRightText rightText = null;
        //获取当前要显示的type，根据type区分数据加载
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    leftText = new ViewHolderLeftText();
                    view = inflater.inflate(R.layout.left_item, null);
                    leftText.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    //设置缓存
                    view.setTag(leftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    rightText = new ViewHolderRightText();
                    view = inflater.inflate(R.layout.right_item, null);
                    rightText.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(rightText);
                    break;
            }
        } else {
            switch (type) {
                case VALUE_LEFT_TEXT:
                    leftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    rightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }
        ChatData data = chatDataList.get(i);
        switch (type) {
            case VALUE_LEFT_TEXT:
                leftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                rightText.tv_right_text.setText(data.getText());
                break;
        }
        return view;
    }

    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatData data = chatDataList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的layout数量
    @Override
    public int getViewTypeCount() {
        return 3;//mLisy.size + 1
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
