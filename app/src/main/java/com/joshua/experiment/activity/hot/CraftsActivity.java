package com.joshua.experiment.activity.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.HotMoreCraftAdapter;
import com.joshua.experiment.entity.HotCraftsman;
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

public class CraftsActivity extends BaseActivity {

    @BindView(R.id.hot_crafts_tool_bar)
    Toolbar hotCraftsToolBar;
    @BindView(R.id.hot_crafts_rv)
    RecyclerView hotCraftsRv;

    private List<HotCraftsman> list_DGGJ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_crafts);
        ButterKnife.bind(this);
        initData();
        hotCraftsToolBar.setTitle("");
        setSupportActionBar(hotCraftsToolBar);
    }

    public void initData() {
        list_DGGJ = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getGJ();
    }

    private void getGJ() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_CRAFTSMAN)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                parseGJ(result);
            }

            @Override
            protected void error() {

            }
        });
    }


    private void parseGJ(String result) {
        Gson gson = new Gson();
        list_DGGJ = gson.fromJson(result, new TypeToken<List<HotCraftsman>>() {
        }.getType());
        if (list_DGGJ.get(0).getCraftsmanName().equals("null")) {
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
                    initRecycleGJ();
                }
            });
        }
    }

    private void initRecycleGJ() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotCraftsRv.setLayoutManager(linearLayoutManager);
        HotMoreCraftAdapter adapter = new HotMoreCraftAdapter(this,list_DGGJ);
        adapter.setOnRecyclerViewItemClickListener(new HotMoreCraftAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, CraftsHomeActivity.class);
                intent.putExtra("craftsName", list_DGGJ.get(pos).getCraftsmanName());
                intent.putExtra("craftsAccount", list_DGGJ.get(pos).getCraftsAccount());
                intent.putExtra("craftsIntro", list_DGGJ.get(pos).getIntroduction());
                intent.putExtra("craftsClassify", list_DGGJ.get(pos).getClassifyCrafts());
                intent.putExtra("craftsHotDegree", list_DGGJ.get(pos).getHotDegree());
                intent.putExtra("craftsPic", list_DGGJ.get(pos).getImageUrl());
                intent.putExtra("isFocus",list_DGGJ.get(pos).getIsFocus());
                mBaseActivity.startActivity(intent);
            }
        });
        hotCraftsRv.setAdapter(adapter);
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
