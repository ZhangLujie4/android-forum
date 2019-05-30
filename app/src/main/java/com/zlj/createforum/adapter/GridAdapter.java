package com.zlj.createforum.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zlj.createforum.R;
import com.zlj.createforum.entity.PictureData;

import java.util.List;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.adapter
 * 文件名:   GridAdapter
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 23:28
 * 描述:    Grid的Adapter适配器
 */

public class GridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private PictureData data;
    private List<PictureData> mList;
    private WindowManager wm;
    private int width;

    public GridAdapter(Context context, List<PictureData> mList) {
        this.context = context;
        this.mList = mList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridHolder gridHolder = null;
        if (view == null) {
            gridHolder = new GridHolder();
            view = inflater.inflate(R.layout.picture_item, null);
            gridHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(gridHolder);
        } else {
            gridHolder = (GridHolder) view.getTag();
        }
        data = mList.get(i);
        //解析图片
        String url = data.getUrl();
        //PicassoUtils.loadImageViewSize(context, url, width/2, 540, gridHolder.imageView);
        return view;
    }

    class GridHolder {
        private ImageView imageView;
    }
}
