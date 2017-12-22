package com.joshua.experiment.fragment.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.adapter.classify.ClassifyAdapter;
import com.joshua.experiment.entity.Classify;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nzz on 2017/6/24.
 * 首页-分类-房屋建筑Fragment
 */

public class HousesFragment extends BaseFragment {

    @BindView(R.id.classify_house_rv)
    RecyclerView classifyHouseRv;

    private List<Classify> list_house;

    public static Fragment newInstance() {
        HousesFragment fragment = new HousesFragment();
        return fragment;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.classify_houses, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_house = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getHouse(getResources().getString(R.string.type1));
    }

    private void getHouse(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        String requestStr = "method=" + Server.HOME_CLASSIFY + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(Server.MEDIA_TYPE_MARKDOWN,requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseHouse(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseHouse(String result) {
        Gson gson = new Gson();
        list_house = gson.fromJson(result, new TypeToken<List<Classify>>() {
        }.getType());
        if (list_house.get(0).getTitle().equals("null")) {
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
                    initRecycleHouse();
                }
            });
        }
    }

    private void initRecycleHouse() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        classifyHouseRv.setLayoutManager(linearLayoutManager);
        ClassifyAdapter adapter = new ClassifyAdapter(getActivity(), list_house);
        adapter.setOnRecyclerViewItemClickListener(new ClassifyAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_house.get(pos).getId());
                intent.putExtra("albumName", list_house.get(pos).getTitle());
                intent.putExtra("albumPic", list_house.get(pos).getAlbumImage());
                intent.putExtra("albumCrafts", list_house.get(pos).getCraftsmanName());
                intent.putExtra("albumIntroduction", list_house.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_house.get(pos).getClassify());
                intent.putExtra("albumModel", list_house.get(pos).getModel());
                intent.putExtra("albumPlay", list_house.get(pos).getPlay());
                intent.putExtra("albumSubscribe", list_house.get(pos).getSubscribe());
                mContext.startActivity(intent);
            }
        });
        classifyHouseRv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_classify_b);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
