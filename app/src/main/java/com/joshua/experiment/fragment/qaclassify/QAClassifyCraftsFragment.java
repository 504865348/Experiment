package com.joshua.experiment.fragment.qaclassify;

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
import com.joshua.experiment.activity.qaclassify.QAClassifyActivity;
import com.joshua.experiment.adapter.qafragment.ClassCraftAdapter;
import com.joshua.experiment.entity.ClassCraft;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by nzz on 2017/7/12.
 * 问答Fragment-9大分类-工匠模块
 */

public class QAClassifyCraftsFragment extends BaseFragment {
    @BindView(R.id.question_answer_crafts_rv)
    RecyclerView questionAnswerCraftsRv;

    private List<ClassCraft> list_craft;
    private String classifyCrafts = QAClassifyActivity.classifyCraftsFlag;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.question_answer_class_crafts, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_craft = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getClassifyCrafts(classifyCrafts);
    }

    private void getClassifyCrafts(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        String requestStr = "method=" + Server.CLASSIFY_CRAFTS_LIST_BY_NAME + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(MEDIA_TYPE_MARKDOWN,requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseClassifyCrafts(result);
            }
            @Override
            protected void error() {
            }
        });
    }
    private void parseClassifyCrafts(String result) {
        Gson gson = new Gson();
        list_craft = gson.fromJson(result, new TypeToken<List<ClassCraft>>() {
        }.getType());
        if (list_craft.get(0).getCraftsmanName().equals("null")) {
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
                    initLayout();
                }
            });
        }
    }
    private void initLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        questionAnswerCraftsRv.setLayoutManager(linearLayoutManager);
        ClassCraftAdapter adapter = new ClassCraftAdapter(getActivity(), list_craft);
        adapter.setOnRecyclerViewItemClickListener(new ClassCraftAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, CraftsHomeActivity.class);
                intent.putExtra("craftsName", list_craft.get(pos).getCraftsmanName());
                intent.putExtra("craftsAccount", list_craft.get(pos).getCraftsAccount());
                intent.putExtra("craftsIntro", list_craft.get(pos).getIntroduction());
                intent.putExtra("craftsClassify", list_craft.get(pos).getClassifyCrafts());
                intent.putExtra("craftsHotDegree", list_craft.get(pos).getHotDegree());
                intent.putExtra("craftsPic", list_craft.get(pos).getImageUrl());
                mContext.startActivity(intent);
            }
        });
        questionAnswerCraftsRv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_layout);
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
