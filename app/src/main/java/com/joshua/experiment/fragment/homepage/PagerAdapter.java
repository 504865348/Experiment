package com.joshua.experiment.fragment.homepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.joshua.experiment.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    List<BaseFragment> listFragment = new ArrayList<>();

    public PagerAdapter(FragmentManager fm, List<BaseFragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
    }


    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

}
