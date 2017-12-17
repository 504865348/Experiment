package com.joshua.experiment.activity.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.info.SubscribeAdapter;
import com.joshua.experiment.entity.MySubscribe;
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

public class SubscribeActivity extends BaseActivity {

    @BindView(R.id.subscribe_rv)
    RecyclerView subscribeRv;

    private OkHttpClient mClient;
    private List<MySubscribe> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_subscribe);
        ButterKnife.bind(this);
        initData();
    }

    public void initData() {
        list = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {

        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.MY_SUBSCRIBE)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                Log.d(TAG, "success: " + result);
                parseData(result);
            }

            @Override
            protected void error() {

            }
        });

    }

    private void parseData(String result) {
        Gson gson = new Gson();
        list = gson.fromJson(result, new TypeToken<List<MySubscribe>>() {
        }.getType());
        if (list.get(0).getProgramName().equals("null")) {
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        subscribeRv.setLayoutManager(linearLayoutManager);
        SubscribeAdapter adapter = new SubscribeAdapter(this, list);
        adapter.setOnRecyclerViewItemClickListener(new SubscribeAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list.get(pos).getId());
                intent.putExtra("albumName", list.get(pos).getProgramName());
                intent.putExtra("albumPic", list.get(pos).getUrl());
                intent.putExtra("albumCrafts", list.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list.get(pos).getClassify());
                intent.putExtra("albumModel", list.get(pos).getModel());
                intent.putExtra("albumPlay", list.get(pos).getPlay());
                mBaseActivity.startActivity(intent);
            }
        });
        subscribeRv.setAdapter(adapter);
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) mBaseActivity.findViewById(R.id.empty);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

}
