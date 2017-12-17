package com.joshua.experiment.fragment.findfriendpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.find.FindFriendsAdapter;
import com.joshua.experiment.entity.FindFriendsAttention;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
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

public class AttentionPager extends BaseFragment {

    @BindView(R.id.find_friends_recommend_rv)
    RecyclerView findFriendsRecommendRv;

    private List<FindFriendsAttention> list_FF;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.find_friends_recommend, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_FF = new ArrayList<>();
        getDataFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getDataFromServer() {
        getFF();
    }

    private void getFF() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_CRAFTSMAN)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseFF(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseFF(String result) {
        Gson gson = new Gson();
        list_FF = gson.fromJson(result, new TypeToken<List<FindFriendsAttention>>() {
        }.getType());
        if (list_FF.get(0).getCraftsmanName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false);
                    initRecycleFF();
                }
            });
        }
    }

    private void initRecycleFF() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        findFriendsRecommendRv.setLayoutManager(linearLayoutManager);
        FindFriendsAdapter adapter = new FindFriendsAdapter(getActivity(),list_FF);
        adapter.setOnRecyclerViewItemClickListener(new FindFriendsAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, CraftsHomeActivity.class);
                intent.putExtra("craftsName", list_FF.get(pos).getCraftsmanName());
                intent.putExtra("craftsAccount", list_FF.get(pos).getCraftsAccount());
                intent.putExtra("craftsIntro", list_FF.get(pos).getIntroduction());
                intent.putExtra("craftsClassify", list_FF.get(pos).getClassifyCrafts());
                intent.putExtra("craftsHotDegree", list_FF.get(pos).getHotDegree());
                intent.putExtra("craftsPic", list_FF.get(pos).getImageUrl());
                mContext.startActivity(intent);
            }
        });
        findFriendsRecommendRv.setAdapter(adapter);
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_layout);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }
}
