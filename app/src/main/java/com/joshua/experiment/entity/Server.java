package com.joshua.experiment.entity;


import okhttp3.MediaType;

/**
 * Created by nzz on 2017/5/11.
 * methods
 */

public class Server {

//    public static final String SERVER_REMOTE = "http://10.10.98.148:8080/MainServlet";
//    public static final String SERVER_REMOTE = "http://139.224.35.126:8080/GJ/MainServlet";
    public static final String SERVER_UPLOAD = "http://139.224.35.126:8080/GJ_AND/UploadServlet";
    public static final String SERVER_VIDEO = "http://139.224.35.126:8080/GJ_AND/VedioServlet";
    public static final String SERVER_ALBUM = "http://139.224.35.126:8080/GJ_AND/AlbumServlet";
    public static final String SERVER_RECORD = "http://139.224.35.126:8080/GJ_AND/MyRecordingServlet";

    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    public static final String SERVER_REMOTE = "http://139.224.35.126:8080/GJ_AND/MainServlet";
//    public static final String SERVER_UPLOAD = "http://139.224.35.126:8080/GJ_AND/UploadServlet";
//    public static final String SERVER_VIDEO = "http://139.224.35.126:8080/GJ_AND/VedioServlet";
//    public static final String SERVER_ALBUM = "http://139.224.35.126:8080/GJ_AND/AlbumServlet";
//    public static final String SERVER_RECORD = "http://139.224.35.126:8080/GJ_AND/MyRecordingServlet";
    public static final String SERVER_MY_INFO = "http://139.224.35.126:8080/GJ_AND/MyInfoServlet";

    public static final String SERVER_LOGIN = "Login"; //登录
    public static final String CAROUSEL_PIC = "CarouselPic"; //获取轮播图片
    public static final String HOME_HOT_CRAFTSMAN = "HotCraftsman"; //首页-热门-大国工匠
    public static final String HOME_HOT_SKILLS = "SkillsInfo"; //首页-热门-匠心独运e
    public static final String HOME_HOT_POLICY = "PolicyInfo"; //首页-热门-讲政策
    public static final String HOME_HOT_LISTEN = "ListenInfo"; //首页-热门-听专题
    public static final String HOME_HOT_LOOK = "LookInfo"; //首页-热门-看利器
    public static final String HOME_RECOMMEND = "Recommend"; //首页-推荐
    public static final String HOME_CLASSIFY = "Classify"; //首页-分类
    public static final String HOME_CRAFTSMAN = "Craftsman"; //首页-工匠
    public static final String BILLBOARD = "Billboard"; //榜单--节目榜单排名(每个具体榜单的前2名),工匠榜单排名(前2名)
    public static final String BILLBOARD_HOT = "BillboardHot"; //榜单-最火节目飙升榜
    public static final String BILLBOARD_MORE = "BillboardMore"; //榜单-最多订阅经典榜
    public static final String BILLBOARD_PAY= "BillboardPay"; //榜单-付费精品飙升榜
    public static final String BILLBOARD_CRAFTSMAN = "BillboardCraftsman"; //榜单-最大国工匠榜
    public static final String QUESANS_CLASSIFY = "QuesAnsClassify"; //问答-分类
    public static final String CRAFTS_UNDEAL_ANS= "CraftsmanUnDealAns"; //工匠-我的问答-未处理回答
    public static final String CRAFTS_MY_ANS= "CraftsmanMyAns"; //工匠-我的问答-我的回答
    public static final String CRAFTS_MY_QUES= "CraftsmanMyQues"; //工匠-我的问答-我的提问
    public static final String COMMON_MY_ANS= "NormalDealQues"; //我的问答-已处理回答
    public static final String COMMON_UNDEAL_ANS= "NormalUnDealQues"; //我的问答-未处理回答


    public static final String SERVER_SMS = "sendCode"; //注册验证码
    public static final String SERVER_REGISTER = "Sign"; //注册
    public static final String FEEDBACK = "Feedback"; //意见反馈
    public static final String EDIT_PSW = "EditPsw"; //修改密码
    public static final String ALBUM_LIST = "queryMyAlbum"; //专辑列表
    public static final String QUERY_QUESTION = "queryQuesById"; //获得问题
    public static final String FORGET_PASSWORD = "updatePwd"; //忘记密码
    public static final String SERVER_SMS_FORGET_PWD = "verifyAccount"; //注册验证码
    public static final String SERVER_SEARCH = "fuzzySearch"; //搜索-问答专辑工匠
    public static final String ALBUM_LIST_BY_CRAFTS = "queryMyAlbumByName"; //工匠个人主页--专辑
    public static final String ANS_LIST_BY_CRAFTS = "queryMyAnsByName"; //工匠个人主页--问答
    public static final String CRAFTS_HOME = "CraftsHome"; //专辑主页--详情--工匠
    public static final String PRO_LIST_BY_NAME = "queryProByName"; //专辑主页--节目
    public static final String ALBUM_PLAY = "AlbumPlay"; //专辑播放量(专辑下所有节目的总播放量)
    public static final String ALBUM_SUBSCRIBE = "AlbumSubscribe"; //专辑订阅量
    public static final String ALBUM_BUY = "AlbumBuy"; //专辑购买量
    public static final String PRO_COLLECT = "ProCollect"; //节目收藏量
    public static final String CRAFT_ATTENTION = "CraftAttention"; //工匠关注量
    public static final String CLASSIFY_QUES_ANS_LIST_BY_NAME = "queryClassifyQuesAnsByName"; //问答主界面--分类--问答
    public static final String CLASSIFY_CRAFTS_LIST_BY_NAME = "queryClassifyCraftByName"; //问答主界面--分类--工匠
    public static final String MY_SUBSCRIBE = "MySubscribe"; //我的订阅
    public static final String MY_BILLBOARD_HOT = "MyBillboardHot"; //我的榜单-最火节目飙升榜
    public static final String MY_BILLBOARD_MORE = "MyBillboardMore"; //我的榜单-最多订阅经典榜
    public static final String MY_BILLBOARD_PAY= "MyBillboardPay"; //我的榜单-付费精品飙升榜
    public static final String MY_BILLBOARD_CRAFTSMAN = "MyBillboardCraftsman"; //我的榜单-最大国工匠榜

    public static final String SERVER_SEND_ORDER = "AliPay"; //向服务器发送订单
    public static final String SERVER_SEND_ORDER_RESULT = "orderResult"; //向服务器发送订单结果
    public static final String SERVER_QUERY_ORDER = "queryMyOrder"; //查询订单

    public static final String SERVER_GET_ATTENTION = "concernCraftsman"; //关注
    public static final String SERVER_MY_ATTENTION = "MyConcern"; //我的关注
    public static final String SERVER_ATTENTION_MINE = "Concerned"; //关注我的
    public static final String SERVER_SUBSCRIBE = "albumSubscribe"; //订阅
    public static final String SERVER_MY_SUBSCRIBE = "MySubscribe"; //我的订阅
    public static final String SERVER_COLLECT = "collectProgramme"; //收藏
    public static final String SERVER_MY_COLLECT = "MyCollect"; //我的收藏
    public static final String SERVER_MY_BUY_VIDEO = "alreadyVideoPurchase"; //我的购买-节目
    public static final String SERVER_MY_BUY_QUES= "alreadyQuesPurchase"; //我的购买-问答

    public static final String SERVER_UPDATE= "checkUpdate"; //检查更新
    public static final String SERVER_POST_RECORD= "addMyRecording"; //上传视频信息到服务器
    public static final String SERVER_BUY_TIMES= "queryRecordingBuyTimes"; //查询视频购买次数



}
