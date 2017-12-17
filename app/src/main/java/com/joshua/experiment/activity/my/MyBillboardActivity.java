package com.joshua.experiment.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBillboardActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.my_info_billboard_toolbar)
    Toolbar myInfoBillboardToolbar;
    @BindView(R.id.my_info_billboard_crafts)
    LinearLayout myInfoBillboardCrafts;
    @BindView(R.id.my_info_billboard_hot)
    LinearLayout myInfoBillboardHot;
    @BindView(R.id.my_info_billboard_more)
    LinearLayout myInfoBillboardMore;
    @BindView(R.id.my_info_billboard_pay)
    LinearLayout myInfoBillboardPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_billboard);
        ButterKnife.bind(this);
        myInfoBillboardToolbar.setTitle("");
        setSupportActionBar(myInfoBillboardToolbar);
        initListener();
    }

    private void initListener() {
        myInfoBillboardCrafts.setOnClickListener(this);
        myInfoBillboardHot.setOnClickListener(this);
        myInfoBillboardMore.setOnClickListener(this);
        myInfoBillboardPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_billboard_crafts:
                startActivity(new Intent(mBaseActivity,MyBillboardCraftsActivity.class));
                break;
            case R.id.my_info_billboard_hot:
                startActivity(new Intent(mBaseActivity,MyBillboardHotActivity.class));
                break;
            case R.id.my_info_billboard_more:
                startActivity(new Intent(mBaseActivity,MyBillboardMoreActivity.class));
                break;
            case R.id.my_info_billboard_pay:
                startActivity(new Intent(mBaseActivity,MyBillboardPayActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
