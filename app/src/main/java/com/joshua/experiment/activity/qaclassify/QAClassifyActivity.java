package com.joshua.experiment.activity.qaclassify;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.fragment.qaclassify.QAClassifyCraftsFragment;
import com.joshua.experiment.fragment.qaclassify.QAClassifyQuesFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QAClassifyActivity extends BaseActivity {

    @BindView(R.id.q_a_tv_class)
    TextView qATvClass;
    @BindView(R.id.question_answer_tool_bar)
    Toolbar questionAnswerToolBar;
    @BindView(R.id.q_a_tv_inter_locution)
    TextView qATvInterLocution;
    @BindView(R.id.q_a_tv_crafts)
    TextView qATvCrafts;
    @BindView(R.id.img_tab_line)
    ImageView mTabLine;
    @BindView(R.id.q_a_pager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private PagerAdapter adapter;
    private int screenWidth;
    private QAClassifyQuesFragment mQAClassifyQuesFragment;
    private QAClassifyCraftsFragment mQAClassifyCraftsFragment;
    public static String classifyCraftsFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_answer_class);
        ButterKnife.bind(this);
        questionAnswerToolBar.setTitle("");
        setSupportActionBar(questionAnswerToolBar);
        initPager();
        initTabLineWidth();
        initData();
    }

    private void initData() {
        qATvClass.setText(classifyCraftsFlag);
    }

    private void initPager() {
        classifyCraftsFlag = getIntent().getStringExtra("classifyFlag");
        mQAClassifyQuesFragment = new QAClassifyQuesFragment();
        mQAClassifyCraftsFragment = new QAClassifyCraftsFragment();
        mFragmentList.add(mQAClassifyQuesFragment);
        mFragmentList.add(mQAClassifyCraftsFragment);
        adapter = new com.joshua.experiment.fragment.homepage.PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        qATvInterLocution.setTextColor(Color.RED);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
                lp.leftMargin = screenWidth / 2 * position + positionOffsetPixels / 2;
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        qATvInterLocution.setTextColor(Color.RED);
                        break;
                    case 1:
                        qATvCrafts.setTextColor(Color.RED);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void resetTextView() {
        qATvInterLocution.setTextColor(Color.BLACK);
        qATvCrafts.setTextColor(Color.BLACK);
    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = screenWidth / 2;
        mTabLine.setLayoutParams(lp);
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
