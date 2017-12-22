package com.joshua.experiment.fragment.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;

import com.joshua.experiment.activity.hot.CraftsActivity;
import com.joshua.experiment.activity.hot.ListenActivity;
import com.joshua.experiment.activity.hot.LookActivity;
import com.joshua.experiment.activity.hot.PolicyActivity;
import com.joshua.experiment.activity.hot.SkillsActivity;
import com.joshua.experiment.activity.hot.YouXiaoActivity;
import com.joshua.experiment.adapter.HotCraftsAdapter;
import com.joshua.experiment.adapter.HotListenAdapter;
import com.joshua.experiment.adapter.HotLookAdapter;
import com.joshua.experiment.adapter.HotPolicyAdapter;
import com.joshua.experiment.adapter.HotSkillsAdapter;
import com.joshua.experiment.change.adapter.HotYouXiaoAdapter;
import com.joshua.experiment.change.entity.HotYouXiao;
import com.joshua.experiment.entity.CarouselPic;
import com.joshua.experiment.entity.HotListen;
import com.joshua.experiment.entity.HotLook;
import com.joshua.experiment.entity.HotPolicy;
import com.joshua.experiment.entity.HotSkills;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.http.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class HomeHotPager extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.home_page_hot_banner)
    Banner home_page_hot_banner;
    @BindView(R.id.hot_crafts_rv)
    RecyclerView hot_crafts_rv;
    @BindView(R.id.hot_skills_rv)
    RecyclerView hot_skills_rv;
    @BindView(R.id.hot_policy_rv)
    RecyclerView hot_policy_rv;
    @BindView(R.id.hot_listen_rv)
    RecyclerView hot_listen_rv;
    @BindView(R.id.hot_look_rv)
    RecyclerView hot_look_rv;
    @BindView(R.id.hot_middle_image_crafts)
    ImageView hotMiddleImageCrafts;
    @BindView(R.id.hot_middle_image_skills)
    ImageView hotMiddleImageSkills;
    @BindView(R.id.hot_middle_image_policy)
    ImageView hotMiddleImagePolicy;
    @BindView(R.id.hot_middle_image_listen)
    ImageView hotMiddleImageListen;
    @BindView(R.id.hot_middle_image_look)
    ImageView hotMiddleImageLook;
    @BindView(R.id.home_page_hot_more_crafts)
    TextView homePageHotMoreCrafts;
    @BindView(R.id.home_page_hot_more_skills)
    TextView homePageHotMoreSkills;
    @BindView(R.id.home_page_hot_more_policy)
    TextView homePageHotMorePolicy;
    @BindView(R.id.home_page_hot_more_listen)
    TextView homePageHotMoreListen;
    @BindView(R.id.home_page_hot_more_look)
    TextView homePageHotMoreLook;

    private List<CarouselPic> list_pic;
    private List<HotYouXiao> list_DGGJ;
    private List<HotSkills> list_JXDY;
    private List<HotPolicy> list_JZC;
    private List<HotListen> list_TZT;
    private List<HotLook> list_KLQ;
    private OkHttpClient mClient;
    FrameLayout empty_DGGJ, empty_JXDY, empty_JZC, empty_TZT, empty_KLQ;

    public HomeHotPager() {

    }

    @Override
    public View initView() {
        return View.inflate(getActivity(), R.layout.home_page_hot, null);
    }

    @Override
    public void initData() {
        list_pic = new ArrayList<>();
        list_DGGJ = new ArrayList<>();
        list_JXDY = new ArrayList<>();
        list_JZC = new ArrayList<>();
        list_TZT = new ArrayList<>();
        list_KLQ = new ArrayList<>();
        initListener();
        initEmptyView();
        getDataFromServer();
    }

    private void initListener() {
        hotMiddleImageCrafts.setOnClickListener(this);
        hotMiddleImageSkills.setOnClickListener(this);
        hotMiddleImagePolicy.setOnClickListener(this);
        hotMiddleImageListen.setOnClickListener(this);
        hotMiddleImageLook.setOnClickListener(this);
        homePageHotMoreCrafts.setOnClickListener(this);
        homePageHotMoreSkills.setOnClickListener(this);
        homePageHotMorePolicy.setOnClickListener(this);
        homePageHotMoreListen.setOnClickListener(this);
        homePageHotMoreLook.setOnClickListener(this);
    }

    private void initEmptyView() {
        empty_DGGJ = (FrameLayout) getActivity().findViewById(R.id.empty_classify_a);
        empty_JXDY = (FrameLayout) getActivity().findViewById(R.id.empty_classify_b);
        empty_JZC = (FrameLayout) getActivity().findViewById(R.id.empty_classify_c);
        empty_TZT = (FrameLayout) getActivity().findViewById(R.id.empty_classify_d);
        empty_KLQ = (FrameLayout) getActivity().findViewById(R.id.empty_classify_e);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hot_middle_image_crafts:
                startActivity(new Intent(getActivity(), YouXiaoActivity.class));
                break;
            case R.id.home_page_hot_more_crafts:
                startActivity(new Intent(getActivity(), YouXiaoActivity.class));
                break;
            case R.id.hot_middle_image_skills:
                startActivity(new Intent(getActivity(), SkillsActivity.class));
                break;
            case R.id.home_page_hot_more_skills:
                startActivity(new Intent(getActivity(), SkillsActivity.class));
                break;
            case R.id.hot_middle_image_policy:
                startActivity(new Intent(getActivity(), PolicyActivity.class));
                break;
            case R.id.home_page_hot_more_policy:
                startActivity(new Intent(getActivity(), PolicyActivity.class));
                break;
            case R.id.hot_middle_image_listen:
                startActivity(new Intent(getActivity(), ListenActivity.class));
                break;
            case R.id.home_page_hot_more_listen:
                startActivity(new Intent(getActivity(), ListenActivity.class));
                break;
            case R.id.hot_middle_image_look:
                startActivity(new Intent(getActivity(), LookActivity.class));
                break;
            case R.id.home_page_hot_more_look:
                startActivity(new Intent(getActivity(), LookActivity.class));
                break;
        }
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
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        getBanner();//轮播图
        getDGGJ();//大国工匠
        getJXDY();//初中
        getJZC();//高中
        getTZT();//大学
        getKLQ();//民间
    }

    /**
     * --------------------------------数据获取---------------------------------
     */
    private void getDGGJ() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_YOUXIAO)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseDGGJ(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void getJXDY() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_SKILLS)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseJXDY(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void getJZC() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_POLICY)
                .build();
        Log.d(TAG, "getJZC: " + Server.HOME_HOT_POLICY);

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseJZC(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void getTZT() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_LISTEN)
                .build();
        Log.d(TAG, "getTZT: " + Server.HOME_HOT_LISTEN);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseTZT(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void getKLQ() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.HOME_HOT_LOOK)
                .build();
        Log.d(TAG, "getKLQ: " + Server.HOME_HOT_LOOK);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {

                parseKLQ(result);
            }

            @Override
            protected void error() {
            }
        });
    }


    /**
     * --------------------------------数据解析---------------------------------
     */
    private void parseDGGJ(String result) {
        Gson gson = new Gson();
        list_DGGJ = gson.fromJson(result, new TypeToken<List<HotYouXiao>>() {
        }.getType());
        if (list_DGGJ.get(0).getProgramName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true, empty_DGGJ);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false, empty_DGGJ);
                    initRecycleDGGJ();
                }
            });
        }
    }

    private void parseJXDY(String result) {
        Gson gson = new Gson();
        list_JXDY = gson.fromJson(result, new TypeToken<List<HotSkills>>() {
        }.getType());
        if (list_JXDY.get(0).getProgramName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true, empty_JXDY);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false, empty_JXDY);
                    initRecycleJXDY();
                }
            });
        }
    }

    private void parseJZC(String result) {
        Gson gson = new Gson();
        list_JZC = gson.fromJson(result, new TypeToken<List<HotPolicy>>() {
        }.getType());
        if (list_JZC.get(0).getProgramName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true, empty_JZC);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false, empty_JZC);
                    initRecycleJZC();
                }
            });
        }
    }

    private void parseTZT(String result) {
        Gson gson = new Gson();
        list_TZT = gson.fromJson(result, new TypeToken<List<HotListen>>() {
        }.getType());
        if (list_TZT.get(0).getProgramName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true, empty_TZT);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false, empty_TZT);
                    initRecycleTZT();
                }
            });
        }
    }

    private void parseKLQ(String result) {
        Gson gson = new Gson();
        list_KLQ = gson.fromJson(result, new TypeToken<List<HotLook>>() {
        }.getType());
        if (list_KLQ.get(0).getProgramName().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true, empty_KLQ);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false, empty_KLQ);
                    initRecycleKLQ();
                }
            });
        }
    }

    /**
     * --------------------------------数据展示---------------------------------
     */
    private void initRecycleDGGJ() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_crafts_rv.setLayoutManager(linearLayoutManager);
        hot_crafts_rv.setAdapter(new HotYouXiaoAdapter(getActivity(), list_DGGJ));
    }

    private void initRecycleJXDY() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_skills_rv.setLayoutManager(linearLayoutManager);
        hot_skills_rv.setAdapter(new HotSkillsAdapter(getActivity(), list_JXDY));
    }

    private void initRecycleJZC() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_policy_rv.setLayoutManager(linearLayoutManager);
        hot_policy_rv.setAdapter(new HotPolicyAdapter(getActivity(), list_JZC));
    }

    private void initRecycleTZT() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_listen_rv.setLayoutManager(linearLayoutManager);
        hot_listen_rv.setAdapter(new HotListenAdapter(getActivity(), list_TZT));
    }

    private void initRecycleKLQ() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_look_rv.setLayoutManager(linearLayoutManager);
        hot_look_rv.setAdapter(new HotLookAdapter(getActivity(), list_KLQ));
    }

    /**
     * --------------------------------轮播图---------------------------------
     */
    private void getBanner() {
        RequestBody params = new FormBody.Builder()
                .add("method", Server.CAROUSEL_PIC)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseBanner(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    /**
     * 解析JSON数据-banner
     *
     * @param result
     */
    private void parseBanner(String result) {
        Gson gson = new Gson();
        list_pic = gson.fromJson(result, new TypeToken<List<CarouselPic>>() {
        }.getType());
        List<String> imageUrls = new ArrayList<>();
        List<String> imageNames = new ArrayList<>();
        for (CarouselPic carouselPic : list_pic) {
            imageUrls.add(carouselPic.getImgUrl());
            //2017-10-18 不需要轮播图文字
            imageNames.add("");
//            imageNames.add(carouselPic.getImgName());

        }
        initBanner(imageUrls, imageNames);

    }

    /**
     * 初始化轮播图
     */
    private void initBanner(final List<String> imageUrls, final List<String> imageNames) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                home_page_hot_banner
                        .setImages(imageUrls)
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                        .setBannerAnimation(Transformer.Tablet)
                        .setImageLoader(new GlideImageLoader())
                        .setDelayTime(5000)
                        .start();
            }
        });

    }

    /**
     * 设置空视图
     */
    private void setEmptyView(Boolean isEmpty, FrameLayout empty) {
        if (isEmpty) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();
    }
}
