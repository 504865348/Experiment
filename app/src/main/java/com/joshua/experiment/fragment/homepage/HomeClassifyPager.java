package com.joshua.experiment.fragment.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.classify.ClassifyActivity;
import com.joshua.experiment.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeClassifyPager extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.home_page_classify_construction)
    LinearLayout homePageClassifyConstruction;
    @BindView(R.id.home_page_classify_civilization)
    LinearLayout homePageClassifyCivilization;
    @BindView(R.id.home_page_classify_electric)
    LinearLayout homePageClassifyElectric;
    @BindView(R.id.home_page_classify_road)
    LinearLayout homePageClassifyRoad;
    @BindView(R.id.home_page_classify_water_cons)
    LinearLayout homePageClassifyWaterCons;
    @BindView(R.id.home_page_classify_railway)
    LinearLayout homePageClassifyRailway;
    @BindView(R.id.home_page_classify_mining)
    LinearLayout homePageClassifyMining;
    @BindView(R.id.home_page_classify_airport)
    LinearLayout homePageClassifyAirport;
    @BindView(R.id.home_page_classify_communication)
    LinearLayout homePageClassifyCommunication;

//    private String classiyFlag[] = new String[]{
//            "房屋建筑", "市政公用", "机电工程",
//            "公路", "水利水电", "铁路工程",
//            "矿业工程", "民航机场工程", "通信广电工程"};

//    private String classiyFlag[] = new String[]{
//            "房屋建筑", "市政道路", "城市桥梁",
//            "轨道交通", "给水排水", "城市管道",
//            "园林绿化与附属工程", "农艺", "艺术及其它"};

    public HomeClassifyPager( ) {

    }
    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.home_page_classify, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        homePageClassifyConstruction.setOnClickListener(this);
        homePageClassifyCivilization.setOnClickListener(this);
        homePageClassifyElectric.setOnClickListener(this);
        homePageClassifyRoad.setOnClickListener(this);
        homePageClassifyWaterCons.setOnClickListener(this);
        homePageClassifyRailway.setOnClickListener(this);
        homePageClassifyMining.setOnClickListener(this);
        homePageClassifyAirport.setOnClickListener(this);
        homePageClassifyCommunication.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_page_classify_construction:
                putClassifyFlag(getResources().getString(R.string.type1));
                break;
            case R.id.home_page_classify_civilization:
                putClassifyFlag(getResources().getString(R.string.type2));
                break;
            case R.id.home_page_classify_electric:
                putClassifyFlag(getResources().getString(R.string.type3));
                break;
            case R.id.home_page_classify_road:
                putClassifyFlag(getResources().getString(R.string.type4));
                break;
            case R.id.home_page_classify_water_cons:
                putClassifyFlag(getResources().getString(R.string.type5));
                break;
            case R.id.home_page_classify_railway:
                putClassifyFlag(getResources().getString(R.string.type6));
                break;
            case R.id.home_page_classify_mining:
                putClassifyFlag(getResources().getString(R.string.type7));
                break;
            case R.id.home_page_classify_airport:
                putClassifyFlag(getResources().getString(R.string.type8));
                break;
            case R.id.home_page_classify_communication:
                putClassifyFlag(getResources().getString(R.string.type9));
                break;
        }
    }
    private void putClassifyFlag(String str) {
        Intent intent = new Intent(mContext, ClassifyActivity.class);
        intent.putExtra("classifyFlag", str);
        mContext.startActivity(intent);
    }
}
