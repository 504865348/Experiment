package com.joshua.experiment.fragment.craftHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CraftDetailFragment extends BaseFragment {

    @BindView(R.id.tv_detail)
    TextView tv_detail;


    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.craft_detail, null);

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
        String info=getActivity().getIntent().getStringExtra("craftsIntro");
        tv_detail.setText(info);
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}