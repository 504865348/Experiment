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
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.billboard.BillboardCraftsmanAdapter;
import com.joshua.experiment.entity.BillboardCraftsman;
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

public class BillboardCraftsmanActivity extends BaseActivity {

    @BindView(R.id.billboard_craftsman_rv)
    RecyclerView billboard_craftsman_rv;
    @BindView(R.id.billboard_craftsman_tool_bar)
    Toolbar billboardCraftsmanToolBar;

    private List<BillboardCraftsman> list_craftsman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billboard_craftsman);
        ButterKnife.bind(this);
        initData();
        billboardCraftsmanToolBar.setTitle("");
        setSupportActionBar(billboardCraftsmanToolBar);
    }

    public void initData() {
        list_craftsman = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getCraftsman();
    }

    private void getCraftsman() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.BILLBOARD_CRAFTSMAN)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                parseCraftsman(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseCraftsman(String result) {
        Gson gson = new Gson();
        list_craftsman = gson.fromJson(result, new TypeToken<List<BillboardCraftsman>>() {
        }.getType());
        if (list_craftsman.get(0).getCraftsmanName().equals("null")) {
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
                    initRecycleCraftsman();
                }
            });
        }
    }

    private void initRecycleCraftsman() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        billboard_craftsman_rv.setLayoutManager(linearLayoutManager);
        BillboardCraftsmanAdapter adapter = new BillboardCraftsmanAdapter(this, list_craftsman);
        adapter.setOnRecyclerViewItemClickListener(new BillboardCraftsmanAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mBaseActivity, CraftsHomeActivity.class);
                intent.putExtra("craftsAccount", list_craftsman.get(pos).getCraftsAccount());
                intent.putExtra("craftsName", list_craftsman.get(pos).getCraftsmanName());
                intent.putExtra("craftsIntro", list_craftsman.get(pos).getIntroduction());
                intent.putExtra("craftsClassify", list_craftsman.get(pos).getClassifyCrafts());
                intent.putExtra("craftsHotDegree", list_craftsman.get(pos).getHotDegree());
                intent.putExtra("craftsPic", list_craftsman.get(pos).getImageUrl());
                intent.putExtra("isFocus",list_craftsman.get(pos).getIsFocus());
                mBaseActivity.startActivity(intent);
            }
        });
        billboard_craftsman_rv.setAdapter(adapter);
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

