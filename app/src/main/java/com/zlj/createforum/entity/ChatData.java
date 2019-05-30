package com.zlj.createforum.entity;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.entity
 * 文件名:   ChatData
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 10:15
 * 描述:    对话内容类
 */

public class ChatData {

    //用来区分左右
    private int type;
    //文本
    private String text;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
