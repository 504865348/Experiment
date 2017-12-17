package com.joshua.experiment.activity.classify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.MainActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.fragment.classify.AirportFragment;
import com.joshua.experiment.fragment.classify.CommunicationsFragment;
import com.joshua.experiment.fragment.classify.HighWaysFragment;
import com.joshua.experiment.fragment.classify.HousesFragment;
import com.joshua.experiment.fragment.classify.MechanicalFragment;
import com.joshua.experiment.fragment.classify.MiningFragment;
import com.joshua.experiment.fragment.classify.MunicipalFragment;
import com.joshua.experiment.fragment.classify.RailWayFragment;
import com.joshua.experiment.fragment.classify.WaterConservancyFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager vp_pager;
    @BindView(R.id.classify_tool_bar)
    Toolbar classifyToolBar;

    private String strTitle[] = new String[]{
            "房屋建筑", "市政道路", "城市桥梁",
            "轨道交通", "给水排水", "城市管道",
            "园林绿化与附属工程", "农艺", "艺术及其它"};
    private String itemClassifyFlag;
    private Fragment[] mFragmentArrays = new Fragment[9];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.classify);
        ButterKnife.bind(this);
        classifyToolBar.setOnClickListener(this);
        itemClassifyFlag = getIntent().getStringExtra("classifyFlag");
        initView();
    }

    private void initView() {
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mFragmentArrays[0] = HousesFragment.newInstance();
        mFragmentArrays[1] = MunicipalFragment.newInstance();
        mFragmentArrays[2] = MechanicalFragment.newInstance();
        mFragmentArrays[3] = HighWaysFragment.newInstance();
        mFragmentArrays[4] = WaterConservancyFragment.newInstance();
        mFragmentArrays[5] = RailWayFragment.newInstance();
        mFragmentArrays[6] = MiningFragment.newInstance();
        mFragmentArrays[7] = AirportFragment.newInstance();
        mFragmentArrays[8] = CommunicationsFragment.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        vp_pager.setAdapter(pagerAdapter);
        vp_pager.setCurrentItem(strTitleNumber());
        tabLayout.setupWithViewPager(vp_pager);
    }

    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strTitle[position];
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classify_tool_bar:
                startActivity(new Intent(mBaseActivity, MainActivity.class));
                break;
        }
    }
    private int strTitleNumber() {
        int cursor;
        for (cursor=0; cursor<strTitle.length; cursor++) {
            if (itemClassifyFlag.equals(strTitle[cursor]))
                break;
        }
        return cursor;
    }
}
