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
import com.joshua.experiment.fragment.myqacrafts.MyAnswerFragment;
import com.joshua.experiment.fragment.myqacrafts.MyQuestionFragment;
import com.joshua.experiment.fragment.myqacrafts.NotAnswerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.joshua.experiment.R.id.not_answer_tv;

public class MyAskAnswerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.my_records_tool_bar)
    Toolbar mMyRecordsToolBar;

    @BindView(R.id.not_answer_ll)
    LinearLayout mNotAnswerLl;
    @BindView(R.id.my_answer_ll)
    LinearLayout mMyAnswerLl;
    @BindView(R.id.my_question_ll)
    LinearLayout mMyQuestionLl;

    @BindView(not_answer_tv)
    TextView mNotAnswerTv;
    @BindView(R.id.my_answer_tv)
    TextView mMyAnswerTv;
    @BindView(R.id.my_question_tv)
    TextView mMyQuestionTv;

    @BindView(R.id.id_switch_tab_ll)
    LinearLayout mIdSwitchTabLl;
    @BindView(R.id.tab_line_iv)
    ImageView mTabLineIv;
    @BindView(R.id.my_q_a_page_pager)
    ViewPager mViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private NotAnswerFragment mNotAnswerFragment;
    private MyAnswerFragment mMyAnswerFragment;
    private MyQuestionFragment mMyQuestionFragment;
    private PagerAdapter mPagerAdapter;
    private int screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_ask_answer_crafts);
        ButterKnife.bind(this);
        /**
         * ToolBar
         */
        mMyRecordsToolBar.setTitle("");
        setSupportActionBar(mMyRecordsToolBar);
        /**
         * 设置页面和滑动条
         */
        initPager();
        initTabLineWidth();
        mNotAnswerLl.setOnClickListener(this);
        mMyAnswerLl.setOnClickListener(this);
        mMyQuestionLl.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initPager() {
        mNotAnswerFragment = new NotAnswerFragment();
        mMyAnswerFragment = new MyAnswerFragment();
        mMyQuestionFragment = new MyQuestionFragment();
        mFragmentList.add(mNotAnswerFragment);
        mFragmentList.add(mMyAnswerFragment);
        mFragmentList.add(mMyQuestionFragment);
        /**
         * 适配器
         */
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        mNotAnswerTv.setTextColor(Color.RED);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
                lp.leftMargin = screenWidth / 3 * position + positionOffsetPixels / 3;
                mTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextColor();
                switch (position) {
                    case 0:
                        mNotAnswerTv.setTextColor(Color.RED);
                        break;
                    case 1:
                        mMyAnswerTv.setTextColor(Color.RED);
                        break;
                    case 2:
                        mMyQuestionTv.setTextColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 重置文字颜色
     */
    private void resetTextColor() {
        mNotAnswerTv.setTextColor(Color.BLACK);
        mMyAnswerTv.setTextColor(Color.BLACK);
        mMyQuestionTv.setTextColor(Color.BLACK);
    }

    @OnClick({R.id.not_answer_ll, R.id.my_answer_ll, R.id.my_question_ll})
    public void clickLinear(LinearLayout linearLayout) {
        switch (linearLayout.getId()) {
            case R.id.not_answer_ll:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.my_answer_ll:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.my_question_ll:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 设置滑动条的宽度为屏幕的 1/3 (根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv.getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 监听 toolbar 返回按键
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.not_answer_ll:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.my_answer_ll:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.my_question_ll:
                mViewPager.setCurrentItem(2);
                break;
        }
    }
}
