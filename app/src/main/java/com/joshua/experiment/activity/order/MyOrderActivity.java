package com.joshua.experiment.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.info.OrderAdapter;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.Order;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyOrderActivity extends BaseActivity {
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id. myrecorder_toolbar)
    Toolbar myrecorder_toolbar;
    private OkHttpClient mClient;
    private List<Order> list_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        ButterKnife.bind(this);
        myrecorder_toolbar.setTitle("");
        setSupportActionBar(myrecorder_toolbar);
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
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_QUERY_ORDER)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(mBaseActivity) {
            @Override
            protected void success(String result) {
                parseOrder(result);
            }

            @Override
            protected void error() {

            }
        });

    }

    private void parseOrder(String result) {
        Gson gson = new Gson();
        list_order = gson.fromJson(result, new TypeToken<List<Order>>() {
        }.getType());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecycle();
            }
        });
    }

    private void initRecycle() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mBaseActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_order.setLayoutManager(linearLayoutManager);
        if (list_order.get(0).getId() == null || list_order.get(0).getId().equals("null")) {
            findViewById(R.id.tv_empty).setVisibility(View.VISIBLE);
            rv_order.setVisibility(View.GONE);
        } else {
            findViewById(R.id.tv_empty).setVisibility(View.GONE);
            rv_order.setVisibility(View.VISIBLE);
            rv_order.setAdapter(new OrderAdapter(mBaseActivity, list_order));
        }

    }
}
