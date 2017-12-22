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

public class PolicyActivity extends BaseActivity {

    @BindView(R.id.hot_policy_tool_bar)
    Toolbar hotPolicyToolBar;
    @BindView(R.id.hot_policy_rv)
    RecyclerView hotPolicyRv;

    private List<HotSkills> list_JZC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_policy);
        ButterKnife.bind(this);
        initData();
        hotPolicyToolBar.setTitle("");
        setSupportActionBar(hotPolicyToolBar);
    }

    public void initData() {
        list_JZC = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getJZC();//高中
    }

    private void getJZC() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_POLICY)
                .build();
        Log.d(TAG, "getJZC: " + Server.HOME_HOT_POLICY);

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                parseJZC(result);
            }

            @Override
            protected void error() {

            }
        });
    }


    private void parseJZC(String result) {
        Gson gson = new Gson();
        list_JZC = gson.fromJson(result, new TypeToken<List<HotSkills>>() {
        }.getType());
        if (list_JZC.get(0).getProgramName().equals("null")) {
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
                    initRecycleJZC();
                }
            });
        }
    }

    private void initRecycleJZC() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotPolicyRv.setLayoutManager(linearLayoutManager);
        HotMoreAlbumAdapter adapter = new HotMoreAlbumAdapter(this, list_JZC);
        adapter.setOnRecyclerViewItemClickListener(new HotMoreAlbumAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_JZC.get(pos).getId());
                intent.putExtra("albumName", list_JZC.get(pos).getProgramName());
                intent.putExtra("albumPic", list_JZC.get(pos).getImageUrl());
                intent.putExtra("albumCrafts", list_JZC.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list_JZC.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_JZC.get(pos).getClassify());
                intent.putExtra("albumModel", list_JZC.get(pos).getModel());
                intent.putExtra("albumPlay", list_JZC.get(pos).getPlay());
                mBaseActivity.startActivity(intent);
            }
        });
        hotPolicyRv.setAdapter(adapter);
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
