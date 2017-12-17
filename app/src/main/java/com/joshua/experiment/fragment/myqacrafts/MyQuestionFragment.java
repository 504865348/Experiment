package com.joshua.experiment.fragment.myqacrafts;

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
import com.joshua.experiment.adapter.qacrafts.QuesAdapter;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.QuesAnsClassify;
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

public class MyQuestionFragment extends BaseFragment {

    @BindView(R.id.crafts_ques_rv)
    RecyclerView craftsQuesRv;

    private View mView;
    private List<QuesAnsClassify> list_TW;

    public MyQuestionFragment() {

    }

    @Override
    public View initView() {
        mView = View.inflate(getActivity(), R.layout.my_ask_answer_crafts_ques, null);
        return mView;
    }

    @Override
    public void initData() {
        list_TW = new ArrayList<>();
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
        getTW();
    }

    private void getTW() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.CRAFTS_MY_QUES)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseTW(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseTW(String result) {
        Gson gson = new Gson();
        list_TW = gson.fromJson(result, new TypeToken<List<QuesAnsClassify>>() {
        }.getType());
        if(list_TW.get(0).getId()==null||list_TW.get(0).getId().equals("null")){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true);
                }
            });
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false);
                    initRecycleTW();
                }
            });
        }
    }

    private void initRecycleTW() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        craftsQuesRv.setLayoutManager(linearLayoutManager);
        craftsQuesRv.setAdapter(new QuesAdapter(getActivity(), list_TW));
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_classify_b);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }
}
