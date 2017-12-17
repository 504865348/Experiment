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
 * 首页-分类-通信广电工程Fragment
 */

public class CommunicationsFragment extends BaseFragment {

    @BindView(R.id.classify_communications_rv)
    RecyclerView classifyCommunicationsRv;

    private List<Classify> list_communications;

    public static Fragment newInstance() {
        CommunicationsFragment fragment = new CommunicationsFragment();
        return fragment;
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.classify_communications, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_communications = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getCommunications("艺术及其它");
    }

    private void getCommunications(String keyWord) {
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
                parseCommunications(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseCommunications(String result) {
        Gson gson = new Gson();
        list_communications = gson.fromJson(result, new TypeToken<List<Classify>>() {
        }.getType());
        if(list_communications.get(0).getTitle().equals("null")) {
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
                    initRecycleCommunications();
                }
            });
        }
    }

    private void initRecycleCommunications() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        classifyCommunicationsRv.setLayoutManager(linearLayoutManager);
        ClassifyAdapter adapter = new ClassifyAdapter(getActivity(), list_communications);
        adapter.setOnRecyclerViewItemClickListener(new ClassifyAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_communications.get(pos).getId());
                intent.putExtra("albumName", list_communications.get(pos).getTitle());
                intent.putExtra("albumPic", list_communications.get(pos).getAlbumImage());
                intent.putExtra("albumCrafts", list_communications.get(pos).getCraftsmanName());
                intent.putExtra("albumIntroduction", list_communications.get(pos).getIntroduction());
                intent.putExtra("albumClassify", list_communications.get(pos).getClassify());
                intent.putExtra("albumModel", list_communications.get(pos).getModel());
                intent.putExtra("albumPlay", list_communications.get(pos).getPlay());
                intent.putExtra("albumSubscribe", list_communications.get(pos).getSubscribe());
                mContext.startActivity(intent);
            }
        });
        classifyCommunicationsRv.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty);
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
