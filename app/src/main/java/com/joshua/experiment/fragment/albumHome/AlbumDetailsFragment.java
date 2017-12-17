package com.joshua.experiment.fragment.albumHome;

import android.content.Intent;
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
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.albumhome.AlbumDetailsAdapter;
import com.joshua.experiment.entity.AlbumHomeDetails;
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
 * Created by nzz on 2017/6/20.
 * 专辑主页-详情Fragment
 */

public class AlbumDetailsFragment extends BaseFragment {

    @BindView(R.id.album_home_craft_rv)
    RecyclerView albumHomeCraftRv;
    @BindView(R.id.album_home_item_introduction)
    TextView albumHomeItemIntroduction;

    private List<AlbumHomeDetails> list_albumCraft;
    private String craftName = AlbumHomeActivity.homeAlbumCraftName;
    private String albumIntroduction = AlbumHomeActivity.homeAlbumIntroduction;
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.album_home_details, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_albumCraft = new ArrayList<>();
        initAlbumIntro();
        getDataFromServer();
    }

    private void initAlbumIntro() {
        if (albumIntroduction.equals(null)) {
            setEmptyView(true);
        }
        else {
            setEmptyView(false);
            albumHomeItemIntroduction.setText(albumIntroduction);
        }
    }

    private void getDataFromServer() {
        getAlbumCraft(craftName);
    }

    private void getAlbumCraft(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        String requestStr = "method=" + Server.CRAFTS_HOME + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(MEDIA_TYPE_MARKDOWN,requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseAlbumCraft(result);
            }

            @Override
            protected void error() {
            }
        });
    }

    private void parseAlbumCraft(String result) {
        Gson gson = new Gson();
        list_albumCraft = gson.fromJson(result, new TypeToken<List<AlbumHomeDetails>>() {
        }.getType());
        if(list_albumCraft.get(0).getCraftsmanName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setCraftEmptyView(true);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setCraftEmptyView(false);
                    initLayout();
                }
            });
        }
    }

    private void initLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumHomeCraftRv.setLayoutManager(linearLayoutManager);
        AlbumDetailsAdapter adapter = new AlbumDetailsAdapter(getActivity(), list_albumCraft);
        adapter.setOnRecyclerViewItemClickListener(new AlbumDetailsAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, CraftsHomeActivity.class);
                intent.putExtra("craftsName", list_albumCraft.get(pos).getCraftsmanName());
                intent.putExtra("craftsAccount", list_albumCraft.get(pos).getCraftsAccount());
                intent.putExtra("craftsIntro", list_albumCraft.get(pos).getIntroduction());
                intent.putExtra("craftsClassify", list_albumCraft.get(pos).getClassifyCrafts());
                intent.putExtra("craftsHotDegree", list_albumCraft.get(pos).getHotDegree());
                intent.putExtra("craftsPic", list_albumCraft.get(pos).getImageUrl());
                intent.putExtra("isFocus", list_albumCraft.get(pos).getIsFocus());
                mContext.startActivity(intent);
            }
        });
        albumHomeCraftRv.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setCraftEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_classify_a);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
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
