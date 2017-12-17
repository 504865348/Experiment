package com.joshua.experiment.activity.download;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
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
import com.joshua.experiment.fragment.download.AlbumFragment;
import com.joshua.experiment.fragment.download.DownloadingFragment;
import com.joshua.experiment.fragment.download.ProgramFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.download_toolbar) Toolbar mToolbar;
    @BindView(R.id.download_tv_album) TextView mDownloadTvAlbum;
    @BindView(R.id.download_ll_album) LinearLayout mDownloadLlAlbum;
    @BindView(R.id.download_tv_program) TextView mDownloadTvProgram;
    @BindView(R.id.download_ll_program) LinearLayout mDownloadLlProgram;
    @BindView(R.id.download_tv_downloading) TextView mDownloadTvDownloading;
    @BindView(R.id.download_ll_downloading) LinearLayout mDownloadLlDownloading;
    @BindView(R.id.download_switch_tab) LinearLayout mDownloadSwitchTab;
    @BindView(R.id.download_bar) ImageView mTabLine;
    @BindView(R.id.download_text_used) TextView mDownloadTextUsed;
    @BindView(R.id.download_text_useful) TextView mDownloadTextUseful;
    @BindView(R.id.download_page_pager) ViewPager mViewPager;

    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private PagerAdapter adapter;
    private int screenWidth;
    private AlbumFragment mAlbumFragment;
    private ProgramFragment mProgramFragment;
    private DownloadingFragment mDownloadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        initPager();
        initTabLineWidth();
        mDownloadLlAlbum.setOnClickListener(this);
        mDownloadLlProgram.setOnClickListener(this);
        mDownloadLlDownloading.setOnClickListener(this);
    }

    /**
     * 初始化滑动 Pager 数据
     */
    private void initPager() {
        mAlbumFragment = new AlbumFragment();
        mProgramFragment = new ProgramFragment();
        mDownloadingFragment = new DownloadingFragment();
        mFragmentList.add(mAlbumFragment);
        mFragmentList.add(mProgramFragment);
        mFragmentList.add(mDownloadingFragment);
        /**
         * 设置适配器和初始选中项
         */
        adapter = new com.joshua.experiment.fragment.homepage.PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mDownloadTvAlbum.setTextColor(Color.RED);
        /**
         * 添加滑动监听器
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * position :当前页面，及点击滑动的页面
             * offset:当前页面偏移的百分比
             * positionOffsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
                lp.leftMargin = screenWidth/3*position + positionOffsetPixels/3;
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mDownloadTvAlbum.setTextColor(Color.RED);
                        break;
                    case 1:
                        mDownloadTvProgram.setTextColor(Color.RED);
                        break;
                    case 2:
                        mDownloadTvDownloading.setTextColor(Color.RED);
                        break;
                }
            }
            /**
             * 滑动中的状态 1: 正在滑动  2: 滑动完毕  3: 无操作
             */
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void resetTextView() {
        mDownloadTvAlbum.setTextColor(Color.BLACK);
        mDownloadTvProgram.setTextColor(Color.BLACK);
        mDownloadTvDownloading.setTextColor(Color.BLACK);
    }

    /**
     * 设置滑动条的宽度为屏幕的 1/3 (根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLine.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_ll_album:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.download_ll_program:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.download_ll_downloading:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 监听返回按键
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
