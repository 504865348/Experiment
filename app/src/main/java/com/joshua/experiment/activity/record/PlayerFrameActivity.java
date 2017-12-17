package com.joshua.experiment.activity.record;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.VideoDetail;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PlayerFrameActivity extends BaseActivity implements View.OnClickListener {

    JCVideoPlayerStandard mJcVideoPlayerStandard;
    @BindView(R.id.video_player_album_cover)
    ImageView video_player_album_cover;
    @BindView(R.id.video_player_album_name)
    TextView video_player_album_name;
    @BindView(R.id.video_player_album_subname)
    TextView video_player_album_subname;
    @BindView(R.id.video_player_publish_time)
    TextView video_player_publish_time;
    @BindView(R.id.video_player_times)
    TextView video_player_times;
    @BindView(R.id.btn_collect)
    Button btn_collect;
    @BindView(R.id.tv_share)
    TextView tv_share;
    @BindView(R.id. btn_back)
    Button  btn_back;

    private VideoDetail mVideoDetail;
    private OkHttpClient mClient;
    private String isFocus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_frame);
        ButterKnife.bind(this);
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        mVideoDetail = (VideoDetail) getIntent().getSerializableExtra("entity");
        //沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        mJcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.jc_video);
        mJcVideoPlayerStandard.setUp(url
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
        Glide.with(this)
                .load("http://139.224.35.126:8080/GJ/upload/background.png")
                .into(mJcVideoPlayerStandard.thumbImageView);
        initView();
    }

    private void initView() {
        Glide.with(this)
                .load(mVideoDetail.getAlbumImage())
                .into(video_player_album_cover);
        video_player_album_name.setText(mVideoDetail.getTitle());
        video_player_album_subname.setText(mVideoDetail.getClassifyName());
        video_player_times.setText(mVideoDetail.getPlayTimes());
        video_player_publish_time.setText(mVideoDetail.getSubscribeTimes());
        isFocus=mVideoDetail.getIsFocus();
        if (isFocus.equals("1")) {
            btn_collect.setText("取消收藏");
        }
        btn_collect.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_collect:
                collect(isFocus);
                break;
            case R.id.tv_share:
//                if(Build.VERSION.SDK_INT>=23){
//                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
//                    ActivityCompat.requestPermissions(this,mPermissionList,123);
//                }

                share();
                break;
            case R.id.btn_back:
              finish();
                break;
        }
    }
    private void changeFocus() {
        if (isFocus.equals("1")) {
            btn_collect.setText("收藏");
            isFocus = "0";
        } else {
            btn_collect.setText("取消收藏");
            isFocus = "1";
        }
    }

    private void collect(String type) {
        type = type.equals("1") ? "0" : "1";
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_COLLECT)
                .add("ProgrammeId", mVideoDetail.getId())
                .add("flag", type)//    收藏/取消收藏 值 1/0
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(mBaseActivity) {
            @Override
            protected void success(String result) {
                if (result.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "操作成功", Toast.LENGTH_SHORT).show();
                            changeFocus();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "操作失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            protected void error() {

            }
        });


    }

    private UMWeb web;
    private SHARE_MEDIA share_media;

    /**
     * 友盟分享
     */
    private void share() {
        share_media=SHARE_MEDIA.WEIXIN.toSnsPlatform().mPlatform;
        web = new UMWeb("http://139.224.35.126:8080/linlin/Gongjiang.apk");
        web.setTitle("必得工匠");
        web.setThumb(new UMImage(this, R.drawable.share_icon));
        web.setDescription("匠者-传技授得解惑——技，技能；得，心得；惑，疑问。颠覆传统技艺传播方式，提供创新的技艺传播交流平台。");

        new ShareAction(this)
                .withText("匠者-传技授得解惑——技，技能；得，心得；惑，疑问。颠覆传统技艺传播方式，提供创新的技艺传播交流平台。")
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
//            SocializeUtils.safeShowDialog(dialog);
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(mBaseActivity,"成功了",Toast.LENGTH_LONG).show();
//            SocializeUtils.safeCloseDialog(dialog);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mBaseActivity,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mBaseActivity,"取消了",Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
