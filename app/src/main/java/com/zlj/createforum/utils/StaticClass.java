package com.zlj.createforum.utils;

/**
 * 项目名:   ZLJapp
 * 包名:     com.zlj.zljapp.utils
 * 文件名:   StaticClass
 * 创建者:   zhanglujie
 * 创建时间: 2018/6/9 13:01
 * 描述:    数据/常量
 */

public class StaticClass {

    // 请求域名前缀
    public static final String REQUEST_PREFIX = "http://www.anhaozhang.com:8089";

    // 文章详情
    public static final String REQUEST_DETAIL = "http://www.anhaozhang.com/outer/";

    // 默认头像
    public static final String default_avatar = "https://create-forum.oss-cn-beijing.aliyuncs.com/user_avatar.jpg";

    //闪屏延时
    public static final int HANDLER_SPLASH = 1001;

    //判断程序是否第一次运行
    public static final String SHARE_IS_FIRST = "isFirst";

    //Bubly key
    public static final String BUGLY_APP_ID = "6555a03594";

    //Bomb key
    public static final String Bomb_APP_ID = "aca8ca9e940203451c4face9d7ba9022";

    //快递key(聚众数据)
    public static final String COURIER_KEY = "0d96a71252325bdcf30b821e9e80727c";
    //http://v.juhe.cn/exp/index?key=key&com=sf&no=575677355677
    //手机归属地查询
    public static final String PHONE_KEY = "bfb8c930446b6ecf29413079d333e5e7";
    //http://apis.juhe.cn/mobile/get?phone=13429667914&key=您申请的KEY

    //图灵机器人
    public static final String ROBOT_KEY = "990574fe6e284e22a21882b2057c13db";

    //微信精选
    public static final String WECHAT_KEY = "f73f8992f7cfc7abba3bb789b00f1486";
    //http://v.juhe.cn/weixin/query?key=您申请的KEY

    //图片url不过要转码一下http://gank.io/api/data/福利/10/1
    public static final int BEGIN_NUM = 500;
    //http://image.baidu.com/channel/listjson?pn=0&rn=100&tag1=%E5%AE%A0%E7%89%A9&tag2=%E5%85%A8%E9%83%A8&ie=utf8

    //语音key(科大讯飞) 5b247774
    public static final String VOICE_KEY = "5b247774";

    //短信的action
    public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    //版本更新配置文件
    public static final String CHECK_UPDATE_URL = "http://47.95.215.194/zhanglujie/config.json";
}
