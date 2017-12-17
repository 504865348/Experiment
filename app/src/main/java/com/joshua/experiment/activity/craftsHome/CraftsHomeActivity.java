package com.joshua.experiment.activity.craftsHome;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.ask.AskQuestionActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.fragment.craftHome.CraftAlbumFragment;
import com.joshua.experiment.fragment.craftHome.CraftDetailFragment;
import com.joshua.experiment.fragment.craftHome.CraftQAFragment;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.joshua.experiment.R.id.crafts_ll_album;

public class CraftsHomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.crafts_image_icon)
    ImageView mCraftsImageIcon;
    @BindView(R.id.crafts_text_name)
    TextView mCraftsTextName;
    @BindView(R.id.crafts_text_introduction)
    TextView mCraftsTextIntroduction;
    @BindView(R.id.crafts_tv_album)
    TextView mCraftsTvAlbum;
    @BindView(crafts_ll_album)
    LinearLayout mCraftsLlAlbum;
    @BindView(R.id.crafts_tv_q_a)
    TextView mCraftsTvQA;
    @BindView(R.id.crafts_ll_q_a)
    LinearLayout mCraftsLlQA;
    @BindView(R.id.crafts_ll_switch)
    LinearLayout mCraftsLlSwitch;
    @BindView(R.id.img_tab_line)
    ImageView mTabLine;
    @BindView(R.id.crafts_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.crafts_image_follow)
    Button mCraftsImageFollow;
    @BindView(R.id.crafts_image_ask)
    Button mCraftsImageAsk;
    @BindView(R.id.crafts_text_classify)
    TextView craftsTextClassify;
    @BindView(R.id.crafts_text_hot_greed)
    TextView craftsTextHotGreed;
    @BindView(R.id.crafts_tv_detail)
    TextView crafts_tv_detail;
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private PagerAdapter adapter;
    private int screenWidth;
    private CraftAlbumFragment mCraftAlbumFragment;
    private CraftQAFragment mCraftQAFragment;
    private CraftDetailFragment mCraftDetailFragment;
    private String itemCraftsName, itemCraftsIntro, itemCraftsClassify,
            itemCraftsHotDegree, itemCraftsPic, itemCraftsAccount, isFocus;
    public static String homeCraftsName, homeCraftsAccount;
    private OkHttpClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crafts_home);
        ButterKnife.bind(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        initPager();
        initTabLineWidth();
        initView();
        initListener();
    }

    /**
     * 初始化滑动 Pager 数据
     */
    private void initPager() {
        homeCraftsAccount = getIntent().getStringExtra("craftsAccount");
        homeCraftsName = getIntent().getStringExtra("craftsName");
        mCraftDetailFragment=new CraftDetailFragment();
        mCraftAlbumFragment = new CraftAlbumFragment();
        mCraftQAFragment = new CraftQAFragment();
        mFragmentList.add(mCraftDetailFragment);
        mFragmentList.add(mCraftAlbumFragment);
        mFragmentList.add(mCraftQAFragment);
        /**
         * 设置适配器和初始选中项
         */
        adapter = new com.joshua.experiment.fragment.homepage.PagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        crafts_tv_detail.setTextColor(Color.RED);
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
                lp.leftMargin = screenWidth / 3 * position + positionOffsetPixels / 3;
                mTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        crafts_tv_detail.setTextColor(Color.RED);
                        break;
                    case 1:
                        mCraftsTvAlbum.setTextColor(Color.RED);
                        break;
                    case 2:
                        mCraftsTvQA.setTextColor(Color.RED);
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
        crafts_tv_detail.setTextColor(Color.BLACK);
        mCraftsTvAlbum.setTextColor(Color.BLACK);
        mCraftsTvQA.setTextColor(Color.BLACK);
    }

    /**
     * 设置滑动条的宽度为屏幕的 1/2 (根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLine.setLayoutParams(lp);
    }

    private void initView() {
        itemCraftsAccount = getIntent().getStringExtra("craftsAccount");
        itemCraftsName = getIntent().getStringExtra("craftsName");
        itemCraftsPic = getIntent().getStringExtra("craftsPic");
        itemCraftsClassify = getIntent().getStringExtra("craftsClassify");
        itemCraftsHotDegree = getIntent().getStringExtra("craftsHotDegree");
        itemCraftsIntro = getIntent().getStringExtra("craftsIntro");
        isFocus = getIntent().getStringExtra("isFocus");
        mCraftsTextName.setText(itemCraftsName);
        craftsTextClassify.setText(itemCraftsClassify);
        craftsTextHotGreed.setText(itemCraftsHotDegree);
//        mCraftsTextIntroduction.setText(itemCraftsIntro);
        Log.d(TAG, "initView: "+isFocus);
        if (isFocus.equals("1")) {
            mCraftsImageFollow.setText("取消关注");
        }
        Glide.with(this).load(itemCraftsPic).into(mCraftsImageIcon);
    }


    private void changeFocus() {
        if (isFocus.equals("1")) {
            mCraftsImageFollow.setText("关注");
            isFocus = "0";
        } else {
            mCraftsImageFollow.setText("取消关注");
            isFocus = "1";
        }
    }

    private void initListener() {
        mCraftsImageFollow.setOnClickListener(this);
        mCraftsImageAsk.setOnClickListener(this);
        mCraftsLlAlbum.setOnClickListener(this);
        mCraftsLlQA.setOnClickListener(this);
        crafts_tv_detail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crafts_image_ask:
                Intent intent = new Intent(mBaseActivity, AskQuestionActivity.class);
                intent.putExtra("answer", itemCraftsName);
                intent.putExtra("craftsAccount", itemCraftsAccount);
                mBaseActivity.startActivity(intent);
                break;
            case R.id.crafts_image_follow:
                getAttention(isFocus);
                break;
            case R.id.crafts_ll_q_a:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.crafts_tv_detail:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.crafts_ll_album:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    private void getAttention(String type) {
        type = type.equals("1") ? "0" : "1";
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_GET_ATTENTION)
                .add("craftsmanNameId", itemCraftsAccount)
                .add("flag", type)//    关注/取消关注 值 1/0
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(mBaseActivity) {
            @Override
            protected void success(String result) {
                Log.d(TAG, "guanzhu: "+result);
                if (result.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "操作成功", Toast.LENGTH_SHORT).show();
                            changeFocus();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "操作失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            protected void error() {

            }
        });

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