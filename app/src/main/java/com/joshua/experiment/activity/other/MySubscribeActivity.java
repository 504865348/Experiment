package com.joshua.experiment.activity.other;

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
import com.joshua.experiment.adapter.classify.ClassifyAdapter;
import com.joshua.experiment.entity.Classify;
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

public class MySubscribeActivity extends BaseActivity {
    @BindView(R.id. billboard_hot_tool_bar)
    Toolbar billboard_hot_tool_bar;
    @BindView(R.id.rv_subscribe)
    RecyclerView rv_subscribe;

    private List<Classify> list_subscribe;
    private OkHttpClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_subscribe);
        ButterKnife.bind(this);
        init();
        billboard_hot_tool_bar.setTitle("");
        setSupportActionBar(billboard_hot_tool_bar);
    }

    private void init() {
        list_subscribe=new ArrayList<>();
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

    private void getDataFromServer() {
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        RequestBody params=new FormBody.Builder()
                .add("method",Server.SERVER_MY_SUBSCRIBE)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                Log.d("subscribe", "success: "+result);
                parseData(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        list_subscribe = gson.fromJson(result, new TypeToken<List<Classify>>() {
        }.getType());
        if (list_subscribe.get(0).getTitle().equals("null")) {
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
                    initRecycleAirport();
                }
            });
        }
    }

    private void initRecycleAirport() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_subscribe.setLayoutManager(linearLayoutManager);
        ClassifyAdapter adapter = new ClassifyAdapter(this, list_subscribe);
        adapter.setOnRecyclerViewItemClickListener(new ClassifyAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_subscribe.get(pos).getId());
                intent.putExtra("albumName", list_subscribe.get(pos).getTitle());
                intent.putExtra("albumPic", list_subscribe.get(pos).getAlbumImage());
                intent.putExtra("albumCrafts", list_subscribe.get(pos).getCraftsmanName());
                intent.putExtra("albumIntroduction", list_subscribe.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_subscribe.get(pos).getClassify());
                intent.putExtra("albumModel", list_subscribe.get(pos).getModel());
                intent.putExtra("albumPlay", list_subscribe.get(pos).getPlay());
                intent.putExtra("albumSubscribe", list_subscribe.get(pos).getSubscribe());
                startActivity(intent);
            }
        });
        rv_subscribe.setAdapter(adapter);
    }



    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) findViewById(R.id.empty_layout);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }
}
