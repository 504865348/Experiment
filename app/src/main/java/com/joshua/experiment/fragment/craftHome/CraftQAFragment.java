package com.joshua.experiment.fragment.craftHome;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.craftshome.CraftQuesAnsAdapter;
import com.joshua.experiment.entity.CraftHomeAns;
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

public class CraftQAFragment extends BaseFragment {

    @BindView(R.id.crafts_ans_craftsName)
    TextView craftsAnsCraftsName;
    @BindView(R.id.crafts_ans_number)
    TextView craftsAnsNumber;
    @BindView(R.id.craft_q_a_rv)
    RecyclerView craftQARv;

    private List<CraftHomeAns> list_qa;
    private String craftsName = CraftsHomeActivity.homeCraftsName;
    private String craftsAccount = CraftsHomeActivity.homeCraftsAccount;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.craft_q_a, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_qa = new ArrayList<>();
        craftsAnsCraftsName.setText(craftsName);
        getDataFromServer();
    }
    private void getDataFromServer() {
        getCraftAlbum(craftsAccount);
    }
    private void getCraftAlbum(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        String requestStr = "method=" + Server.ANS_LIST_BY_CRAFTS + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(MEDIA_TYPE_MARKDOWN,requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseCraftAlbum(result);
            }
            @Override
            protected void error() {
            }
        });
    }
    private void parseCraftAlbum(String result) {
        Gson gson = new Gson();
        list_qa = gson.fromJson(result, new TypeToken<List<CraftHomeAns>>() {
        }.getType());
        if(list_qa.get(0).getContent()==null||list_qa.get(0).getContent().equals("null")) {
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
        craftQARv.setLayoutManager(linearLayoutManager);
        craftQARv.setAdapter(new CraftQuesAnsAdapter(getActivity(), list_qa));
        craftsAnsNumber.setText(String.valueOf(linearLayoutManager.getItemCount()));
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
