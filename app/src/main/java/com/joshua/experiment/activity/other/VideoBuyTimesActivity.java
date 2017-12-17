package com.joshua.experiment.activity.other;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.BuyTimesAdapter;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.VideoDetailPlus;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class VideoBuyTimesActivity extends BaseActivity {

    @BindView(R.id.billboard_hot_program_rv)
    RecyclerView billboard_hot_program_rv;

    @BindView(R.id. billboard_hot_tool_bar)
    Toolbar billboard_hot_tool_bar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private Call mCall;
    private List<VideoDetailPlus> list_collect;
    private OkHttpClient mClient;
    private File mFile;
    private ProgressDialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            mDialog.setProgress(progress);
        }
    };
    private OkHttpClient mOkHttpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_buy_times);
        ButterKnife.bind(this);
        billboard_hot_tool_bar.setTitle("");
        setSupportActionBar(billboard_hot_tool_bar);
        init();
        tv_title.setText("我的视频");
    }

    private void init() {
        mOkHttpClient = new OkHttpClient();
        list_collect=new ArrayList<>();
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        getDataFromServer();
    }



    private void getDataFromServer() {
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        RequestBody params=new FormBody.Builder()
                .add("method", Server.SERVER_BUY_TIMES)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                Log.d("collect", "success: "+result);
                parseData(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        list_collect = gson.fromJson(result, new TypeToken<List<VideoDetailPlus>>() {
        }.getType());
        if (list_collect.get(0).getRecordTitle().equals("null")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false);
                    initRecycle();
                }
            });
        }
    }

    private void initRecycle() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        billboard_hot_program_rv.setLayoutManager(linearLayoutManager);
        BuyTimesAdapter adapter = new BuyTimesAdapter(this, list_collect);
        billboard_hot_program_rv.setAdapter(adapter);
    }



    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty = (FrameLayout) findViewById(R.id.empty);
        if (isEmpty) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
