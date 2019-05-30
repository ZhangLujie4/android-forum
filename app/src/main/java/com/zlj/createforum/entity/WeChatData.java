package com.zlj.createforum.entity;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.entity
 * 文件名:   WeChatData
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/13 14:30
 * 描述:    微信数据
 */

public class WeChatData {

    private String title;
    private String source;
    private String imgUrl;
    private String newsUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
