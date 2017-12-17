package com.joshua.experiment.activity.answer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.fragment.homepage.PagerAdapter;
import com.joshua.experiment.fragment.myqapublic.MyQuestionFragment;
import com.joshua.experiment.fragment.myqapublic.NotHandleFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAskAnswerCommonActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_qa_common_tool_bar) Toolbar mMyQaCommonToolBar;
    @BindView(R.id.not_handle_tv) TextView mNotHandleTv;
    @BindView(R.id.not_handle_ll) LinearLayout mNotHandleLl;
    @BindView(R.id.my_question_tv) TextView mMyQuestionTv;
    @BindView(R.id.my_question_ll) LinearLayout mMyQuestionLl;
    @BindView(R.id.id_switch_tab_ll) LinearLayout mIdSwitchTabLl;
    @BindView(R.id.tab_line_iv) ImageView mTabLineIv;
    @BindView(R.id.my_q_a_page_pager) ViewPager mViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private NotHandleFragment mNotHandleFragment;
    private MyQuestionFragment mMyQuestionFragment;
    private PagerAdapter mPagerAdapter;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ask_answer_common);
        ButterKnife.bind(this);
        /**
         * ToolBar
         */
        mMyQaCommonToolBar.setTitle("");
        setSupportActionBar(mMyQaCommonToolBar);
        /**
         * Pager
         */
        initPager();
        initTabLineWidth();
        mNotHandleLl.setOnClickListener(this);
        mMyQuestionLl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not_handle_ll:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.my_question_ll:
                mViewPager.setCurrentItem(1);
                break;
        }
    }
    private void initPager() {
        mNotHandleFragment = new NotHandleFragment();
        mMyQuestionFragment = new MyQuestionFragment();
        mFragmentList.add(mNotHandleFragment);
        mFragmentList.add(mMyQuestionFragment);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        mNotHandleTv.setTextColor(Color.RED);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
                lp.leftMargin = screenWidth/2*position + positionOffsetPixels/2;
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextColor();
                switch (position) {
                    case 0:
                        mNotHandleTv.setTextColor(Color.RED);
                        break;
                    case 1:
                        mMyQuestionTv.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void resetTextColor() {
        mNotHandleTv.setTextColor(Color.BLACK);
        mMyQuestionTv.setTextColor(Color.BLACK);
    }

    /**
     * 设置滑动条的宽度为屏幕的 1/2 (根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
        lp.width = screenWidth / 2;
        mTabLineIv.setLayoutParams(lp);
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
