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

public class SkillsActivity extends BaseActivity {

    @BindView(R.id.hot_skills_tool_bar)
    Toolbar hotSkillsToolBar;
    @BindView(R.id.hot_skills_rv)
    RecyclerView hotSkillsRv;

    private List<HotSkills> list_JXDY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hot_skills);
        ButterKnife.bind(this);
        initData();
        hotSkillsToolBar.setTitle("");
        setSupportActionBar(hotSkillsToolBar);
    }

    public void initData() {
        list_JXDY = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getJXDY();//初中
    }

    private void getJXDY() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_SKILLS)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                parseJXDY(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseJXDY(String result) {
        Gson gson = new Gson();
        list_JXDY = gson.fromJson(result, new TypeToken<List<HotSkills>>() {
        }.getType());

        if (list_JXDY.get(0).getProgramName().equals("null")) {
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
                    initRecycleJXDY();
                }
            });
        }
    }
    private void initRecycleJXDY() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        hotSkillsRv.setLayoutManager(linearLayoutManager);
        HotMoreAlbumAdapter adapter = new HotMoreAlbumAdapter(this, list_JXDY);
        adapter.setOnRecyclerViewItemClickListener(new HotMoreAlbumAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_JXDY.get(pos).getId());
                intent.putExtra("albumName", list_JXDY.get(pos).getProgramName());
                intent.putExtra("albumPic", list_JXDY.get(pos).getImageUrl());
                intent.putExtra("albumCrafts", list_JXDY.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list_JXDY.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_JXDY.get(pos).getClassify());
                intent.putExtra("albumModel", list_JXDY.get(pos).getModel());
                intent.putExtra("albumPlay", list_JXDY.get(pos).getPlay());
                mBaseActivity.startActivity(intent);
            }
        });
        hotSkillsRv.setAdapter(adapter);
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
