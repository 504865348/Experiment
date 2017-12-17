package com.joshua.experiment.activity.my;

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
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.craftmy.MyAlbumAdapter;
import com.joshua.experiment.entity.Album;
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

public class MyAlbumActivity extends BaseActivity {

    @BindView(R.id.myAlbum_toolbar)
    Toolbar myAlbumToolbar;
    @BindView(R.id.myAlbum_rv)
    RecyclerView myAlbumRv;

    private OkHttpClient mClient;
    private List<Album> list_album;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_album);
        ButterKnife.bind(this);
        myAlbumToolbar.setTitle("");
        setSupportActionBar(myAlbumToolbar);
        initData();
    }

    public void initData() {
        list_album = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {

        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.ALBUM_LIST)
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
        list_album = gson.fromJson(result, new TypeToken<List<Album>>() {
        }.getType());
        if (list_album.get(0).getTitle().equals("null")) {
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
        myAlbumRv.setLayoutManager(linearLayoutManager);
        myAlbumRv.setAdapter(new MyAlbumAdapter(this, list_album));
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
