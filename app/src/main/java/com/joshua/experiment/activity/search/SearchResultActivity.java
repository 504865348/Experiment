package com.joshua.experiment.activity.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joshua.experiment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.search_interface_back) ImageView img_back;
    @BindView(R.id.search_interface_text) TextView text_back_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        ButterKnife.bind(this);

        setListener();
    }

    private void setListener() {
        img_back.setOnClickListener(this);
        text_back_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_interface_back:
                finish();
                break;
            case R.id.search_interface_text:
                finish();
                break;
        }
    }
}
