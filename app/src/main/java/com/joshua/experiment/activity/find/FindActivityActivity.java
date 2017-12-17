package com.joshua.experiment.activity.find;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FindActivityActivity extends BaseActivity {

    @BindView(R.id.find_activity_tool_bar)
    Toolbar findActivityToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_content_activity);
        ButterKnife.bind(this);
        findActivityToolBar.setTitle("");
        setSupportActionBar(findActivityToolBar);
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
