package com.joshua.experiment.fragment.craftHome;

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
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.craftsHome.CraftsHomeActivity;
import com.joshua.experiment.adapter.craftshome.CraftAlbumAdapter;
import com.joshua.experiment.entity.CraftHomeAlbum;
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

public class CraftAlbumFragment extends BaseFragment {

    @BindView(R.id.craft_album_rv)
    RecyclerView craftAlbumRv;

    private List<CraftHomeAlbum> list_album;
    private String craftsName = CraftsHomeActivity.homeCraftsName;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.craft_album, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_album = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getCraftAlbum(craftsName);
    }


    private void getCraftAlbum(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        String requestStr = "method=" + Server.ALBUM_LIST_BY_CRAFTS + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(Server.MEDIA_TYPE_MARKDOWN,requestStr);
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
        list_album = gson.fromJson(result, new TypeToken<List<CraftHomeAlbum>>() {
        }.getType());
        if (list_album.get(0).getTitle().equals("null")) {
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
        craftAlbumRv.setLayoutManager(linearLayoutManager);
        CraftAlbumAdapter adapter = new CraftAlbumAdapter(getActivity(), list_album);
        adapter.setOnRecyclerViewItemClickListener(new CraftAlbumAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                Intent intent = new Intent(mContext, AlbumHomeActivity.class);
                intent.putExtra("albumId", list_album.get(pos).getAlbumID());
                intent.putExtra("albumName", list_album.get(pos).getTitle());
                intent.putExtra("albumPic", list_album.get(pos).getAlbumImage());
                intent.putExtra("albumCrafts", list_album.get(pos).getAuthor());
                intent.putExtra("albumIntroduction", list_album.get(pos).getIntro());
                intent.putExtra("albumClassify", list_album.get(pos).getClassifyName());
                intent.putExtra("albumModel", list_album.get(pos).getModel());
                intent.putExtra("albumPlay", list_album.get(pos).getPlay());
                intent.putExtra("albumSubscribe", list_album.get(pos).getSubscribe());
                mContext.startActivity(intent);
            }
        });
        craftAlbumRv.setAdapter(adapter);
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
    public void onResume() {
        super.onResume();
        getDataFromServer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}