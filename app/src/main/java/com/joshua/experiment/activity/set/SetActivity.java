package com.joshua.experiment.activity.set;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.account.LoginActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.utils.CacheDataManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.set_ll_bind)
    LinearLayout setLlBind;
    @BindView(R.id.set_ll_change_pwd)
    LinearLayout setLlChangePwd;
    @BindView(R.id.set_ll_recommend)
    LinearLayout setLlRecommend;
    @BindView(R.id.set_ll_help)
    LinearLayout setLlHelp;
    @BindView(R.id.set_ll_about)
    LinearLayout setLlAbout;
    @BindView(R.id.set_btn_exit)
    Button setBtnExit;
    @BindView(R.id.set_ll_clear)
    LinearLayout setLlClear;
    @BindView(R.id.sc_listen)
    SwitchCompat scListen;
    @BindView(R.id.sc_mobile)
    SwitchCompat scMobile;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.set_tool_bar)
    Toolbar set_tool_bar;

    private SharedPreferences sp;
    private boolean isListen, isMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        ButterKnife.bind(this);
        sp = getSharedPreferences("switchCompat.txt", Context.MODE_PRIVATE);
        settingStart();
        initListener();
        listenSwitch();
        showCache();
    }

    private void initListener() {
        set_tool_bar.setOnClickListener(this);
        setLlBind.setOnClickListener(this);
        setLlChangePwd.setOnClickListener(this);
        setLlClear.setOnClickListener(this);
        setLlRecommend.setOnClickListener(this);
        setLlHelp.setOnClickListener(this);
        setLlAbout.setOnClickListener(this);
        setBtnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_ll_bind:
                startActivity(new Intent(mBaseActivity, BindActivity.class));
                break;
            case R.id.set_ll_change_pwd:
                startActivity(new Intent(mBaseActivity, EditPswActivity.class));
                break;
            case R.id.set_ll_clear:
                clear();
                break;
            case R.id.set_ll_recommend:
                Toast.makeText(mBaseActivity, "暂未开放推送设置功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.set_ll_help:
                startActivity(new Intent(mBaseActivity, HelpActivity.class));
                break;
            case R.id.set_ll_about:
                startActivity(new Intent(mBaseActivity, AboutActivity.class));
                break;
            case R.id.set_btn_exit:
                startActivity(new Intent(mBaseActivity, LoginActivity.class));
                break;
            case R.id.set_tool_bar:
                finish();
                break;
        }
    }

    private void listenSwitch() {
        scListen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked == true) {
                    Toast.makeText(mBaseActivity, "开启断点续听", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mBaseActivity, "关闭断点续听", Toast.LENGTH_SHORT).show();
                }
                settingSave();
            }
        });

        scMobile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked == true) {
                    Toast.makeText(mBaseActivity, "允许2G/3G/4G播放和下载", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mBaseActivity, "不允许2G/3G/4G播放和下载", Toast.LENGTH_SHORT).show();
                }
                settingSave();
            }
        });
    }

    private void showCache() {
        try {
            tvCache.setText(CacheDataManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        getConfirmDialog(mBaseActivity, "是否清空占用空间?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Thread(new clearCache()).start();
            }
        }).show();
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mBaseActivity, "清理完成", Toast.LENGTH_SHORT).show();
                    try {
                        //tvCache.setText(CacheDataManager.getTotalCacheSize(mBaseActivity));
                        tvCache.setText("0KB");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    class clearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheDataManager.clearAllCache(mBaseActivity);
                Thread.sleep(3000);
                if (CacheDataManager.getTotalCacheSize(mBaseActivity).startsWith("0")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

    private void settingSave() {
        isListen = scListen.isChecked();
        isMobile = scMobile.isChecked();
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("listen", isListen);
        editor.putBoolean("mobile", isMobile);
        editor.commit();
    }

    private void settingStart() {
        scListen.setChecked(sp.getBoolean("listen", false));
        scMobile.setChecked(sp.getBoolean("mobile", false));
    }
}
