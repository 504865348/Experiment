package com.joshua.experiment.activity.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.HotMoreAlbumAdapter;
import com.joshua.experiment.entity.HotSkills;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LookActivity extends BaseActivity {

    @BindView(R.id.hot_look_tool_bar)
    Toolbar hotLookToolBar;
    @BindView(R.id.hot_look_rv)
    RecyclerView hotLookRv;

    private List<HotSkills> list_KLQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_look);
        ButterKnife.bind(this);
        initData();
        hotLookToolBar.setTitle("");
        setSupportActionBar(hotLookToolBar);
    }

    public void initData() {
        list_KLQ = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getKLQ();//看利器
    }

    private void getKLQ() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_LOOK)
                .build();
        Log.d(TAG, "getKLQ: " + Server.HOME_HOT_LOOK);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {

                parseKLQ(result);
            }

            @Override
            protected void error() {
            }
        });
    }


    private void parseKLQ(String result) {
        Gson gson = new Gson();
        list_KLQ = gson.fromJson(result, new TypeToken<List<HotSkills>>() {
        }.getType());
        if (list_KLQ.get(0).getProgramName().equals("null")) {
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
                    initRecycleKLQ();
                }
            });
        }
    }

    private void initRecycleKLQ() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotLookRv.setLayoutManager(linearLayoutManager);
        HotMoreAlbumAdapter adapter = new HotMoreAlbumAdapter(this, list_KLQ);
        adapter.setOnRecyclerViewItemClickListener(new HotMoreAlbumAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_KLQ.get(pos).getId());
                intent.putExtra("albumName", list_KLQ.get(pos).getProgramName());
                intent.putExtra("albumPic", list_KLQ.get(pos).getImageUrl());
                intent.putExtra("albumCrafts", list_KLQ.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list_KLQ.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_KLQ.get(pos).getClassify());
                intent.putExtra("albumModel", list_KLQ.get(pos).getModel());
                intent.putExtra("albumPlay", list_KLQ.get(pos).getPlay());
                mBaseActivity.startActivity(intent);
            }
        });
        hotLookRv.setAdapter(adapter);
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) mBaseActivity.findViewById(R.id.empty);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();
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
