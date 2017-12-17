package com.joshua.experiment.activity.find;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendCircleActivity extends BaseActivity {


    //@BindView(R.id.find_circle_tool_bar)
   // Toolbar mFindCircleToolBar;
    @BindView(R.id.find_ll_find_friends)
    LinearLayout mFindLlFindFriends;
    @BindView(R.id.find_friend_circle_rv)
    RecyclerView mFindFriendCircleRv;
    @BindView(R.id. find_circle_tool_bar)
    Toolbar find_circle_tool_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_content_circle);
        ButterKnife.bind(this);
        find_circle_tool_bar.setTitle("");
        setSupportActionBar(find_circle_tool_bar);
    }

    @OnClick(R.id.find_ll_find_friends)
    public void onViewClicked() {
        startActivity(new Intent(FriendCircleActivity.this, FindFriendsActivity.class));
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
