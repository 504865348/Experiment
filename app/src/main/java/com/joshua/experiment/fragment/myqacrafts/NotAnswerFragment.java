package com.joshua.experiment.fragment.myqacrafts;

import android.content.Intent;
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
import com.joshua.experiment.activity.ask.CraftsAnswerQuestionActivity;
import com.joshua.experiment.adapter.qacrafts.NotAnswerAdapter;
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

public class NotAnswerFragment extends BaseFragment {

    @BindView(R.id.crafts_not_answer_rv)
    RecyclerView craftsNotAnswerRv;

    private View mView;
    private List<QuesAnsClassify> list_WCL;

    @Override

    public View initView() {
        mView = View.inflate(getActivity(), R.layout.my_ask_answer_crafts_undeal, null);
        return mView;
    }


    @Override
    public void initData() {
        list_WCL = new ArrayList<>();
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
        getWCL();
    }

    private void getWCL() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.CRAFTS_UNDEAL_ANS)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseWCL(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseWCL(String result) {
        Gson gson = new Gson();
        list_WCL = gson.fromJson(result, new TypeToken<List<QuesAnsClassify>>() {
        }.getType());
        if(list_WCL.get(0).getId()==null||list_WCL.get(0).getId().equals("null")){
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
                    initRecycleWCL();
                }
            });
        }
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

    private void initRecycleWCL() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        craftsNotAnswerRv.setLayoutManager(linearLayoutManager);
        NotAnswerAdapter notAnswerAdapter=new NotAnswerAdapter(getActivity(),list_WCL);
        notAnswerAdapter.setOnRecyclerViewItemClickListener(new NotAnswerAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                Intent intent=new Intent(getActivity(),CraftsAnswerQuestionActivity.class);
                intent.putExtra("Id",list_WCL.get(Integer.parseInt(position)).getId());
                intent.putExtra("question",list_WCL.get(Integer.parseInt(position)).getQuestionWord());
                intent.putExtra("pic",list_WCL.get(Integer.parseInt(position)).getQuestionPic());

                Log.d(TAG, "onItemClick: "+position);
                Log.d(TAG, "onItemClick: "+list_WCL.get(Integer.parseInt(position)).getId());
                startActivity(intent);
            }
        });
        craftsNotAnswerRv.setAdapter(notAnswerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();

    }
}
