package com.joshua.experiment.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.find.FindFriendsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindFragment extends BaseFragment implements View.OnClickListener{

    @BindView(R.id.find_ll_friend_circle) LinearLayout findFriend;
    @BindView(R.id.find_ll_activity) LinearLayout findActivity;

    private View view;

    @Override
    public View initView() {
        Log.e("TAG", "Find-->initView()");
        view = View.inflate(mContext, R.layout.find, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        findFriend.setOnClickListener(this);
        findActivity.setOnClickListener(this);
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
            case R.id.find_ll_friend_circle:
                startActivity(new Intent(getActivity(), FindFriendsActivity.class));
                break;
            case R.id.find_ll_activity:
                //startActivity(new Intent(getActivity(), FindActivityActivity.class));
                Toast.makeText(getActivity(), "抱歉，该模块暂未开放", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
