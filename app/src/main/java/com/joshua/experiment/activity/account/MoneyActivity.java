package com.joshua.experiment.activity.account;

import android.os.Bundle;
import android.view.View;


import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

public class MoneyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
    }

    public void pay(View view) {
        //startActivity(new Intent(mBaseActivity,RechargeActivity.class));
    }
}
