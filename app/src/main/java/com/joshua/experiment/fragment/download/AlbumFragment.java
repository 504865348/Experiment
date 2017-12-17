package com.joshua.experiment.fragment.download;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joshua.experiment.R;
import com.joshua.experiment.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Lister on 2017/6/25.
 */

public class AlbumFragment extends BaseFragment {

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.download_album, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
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

}
