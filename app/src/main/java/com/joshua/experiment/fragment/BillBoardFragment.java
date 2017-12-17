package com.joshua.experiment.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.adapter.billboard.BillBoardAdapter;
import com.joshua.experiment.entity.Billboard;
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

public class BillBoardFragment extends BaseFragment {


    @BindView(R.id.billboard_rv)
    RecyclerView billboardRv;

    private View view;
    private List<Billboard> list_billboard;

    @Override
    public View initView() {
        Log.e("TAG", "BillBoard-->initView()");
        view = View.inflate(mContext, R.layout.billboard, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_billboard = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getBillboard();
    }

    private void getBillboard() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.BILLBOARD)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseBillboard(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseBillboard(String result) {
        Gson gson = new Gson();
        list_billboard = gson.fromJson(result, new TypeToken<List<Billboard>>() {
        }.getType());
        if (list_billboard.get(0).getProHotNameTop1().equals("null")) {
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
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        billboardRv.setLayoutManager(linearLayoutManager);
        billboardRv.setAdapter(new BillBoardAdapter(getActivity(), list_billboard));
    }

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
    public void onDestroyView() {
        super.onDestroyView();
    }

}
