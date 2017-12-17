package com.joshua.experiment.activity.billboard;

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
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.billboard.BillboardMoreAdapter;
import com.joshua.experiment.entity.BillboardMore;
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


public class BillboardMoreActivity extends BaseActivity {

    @BindView(R.id.billboard_more_program_rv)
    RecyclerView billboard_more_program_rv;
    @BindView(R.id.billboard_more_tool_bar)
    Toolbar billboardMoreToolBar;

    private List<BillboardMore> list_more;

    //private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billboard_more_program);
        ButterKnife.bind(this);
        initData();
        billboardMoreToolBar.setTitle("");
        setSupportActionBar(billboardMoreToolBar);
    }

    public void initData() {
        list_more = new ArrayList<>();
        getDataFromServer();
    }
/*
    private void initRefreshRecycleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.billboard_more_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }*/

    private void getDataFromServer() {
        getMore();
    }

    private void getMore() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.BILLBOARD_MORE)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                /*mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });*/
                parseMore(result);
            }

            @Override
            protected void error() {
                /*mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });*/
            }
        });
    }

    private void parseMore(String result) {
        Gson gson = new Gson();
        list_more = gson.fromJson(result, new TypeToken<List<BillboardMore>>() {
        }.getType());
        if (list_more.get(0).getTitle().equals("null")) {
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
                    initRecycleMore();
                }
            });
        }
    }

    private void initRecycleMore() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mBaseActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        billboard_more_program_rv.setLayoutManager(linearLayoutManager);
        BillboardMoreAdapter adapter = new BillboardMoreAdapter(mBaseActivity, list_more);
        adapter.setOnRecyclerViewItemClickListener(new BillboardMoreAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_more.get(pos).getAlbumID());
                intent.putExtra("albumName", list_more.get(pos).getTitle());
                intent.putExtra("albumPic", list_more.get(pos).getAlbumImage());
                intent.putExtra("albumCrafts", list_more.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list_more.get(pos).getIntro());
                intent.putExtra("albumClassify", list_more.get(pos).getClassifyName());
                intent.putExtra("albumModel", list_more.get(pos).getModel());
                intent.putExtra("albumPlay", list_more.get(pos).getPlay());
                intent.putExtra("albumSubscribe", list_more.get(pos).getSubscribe());
                mBaseActivity.startActivity(intent);
            }
        });
        billboard_more_program_rv.setAdapter(adapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

