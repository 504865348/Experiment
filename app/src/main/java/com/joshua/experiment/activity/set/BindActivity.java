package com.joshua.experiment.activity.set;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BindActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.bind_tool_bar)
    Toolbar bindToolBar;
    @BindView(R.id.bind_tel)
    LinearLayout bindTel;
    @BindView(R.id.bind_wechat)
    LinearLayout bindWechat;
    @BindView(R.id.bind_qq)
    LinearLayout bindQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_bind);
        ButterKnife.bind(this);
        initListener();
    }
    private void initListener() {
        bindToolBar.setOnClickListener(this);
        bindTel.setOnClickListener(this);
        bindWechat.setOnClickListener(this);
        bindQq.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_tool_bar:
                startActivity(new Intent(mBaseActivity, SetActivity.class));
                break;
            case R.id.bind_tel:
                break;
            case R.id.bind_wechat:
                break;
            case R.id.bind_qq:
                break;
        }
    }
}
