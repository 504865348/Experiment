package com.joshua.experiment.activity.set;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.about_tool_bar)
    Toolbar aboutToolBar;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.set_btn_update)
    Button set_btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_about);
        ButterKnife.bind(this);
        initListener();
        tv_version.setText("当前版本 v" + getLocalVersion());
        set_btn_update.setOnClickListener(this);
    }

    private void initListener() {
        aboutToolBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_tool_bar:
                finish();
                break;
            case R.id.set_btn_update:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {
        set_btn_update.setText("检查中...");
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getApplicationContext()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_UPDATE)
                .add("version", getLocalVersion() + "")
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                try {
                    Log.d("update", "success: " + result);
                    JSONObject jo = new JSONObject(result);
                    String url = jo.getString("url");
                    String shouldUpdate = jo.getString("version");
                    if (shouldUpdate.equals("1")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        intent.setData(content_url);
                        startActivity(intent);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                set_btn_update.setText("检查更新");
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                set_btn_update.setText("当前已是最新版本");
                            }
                        });

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void error() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "检查失败", Toast.LENGTH_SHORT).show();
                        set_btn_update.setText("检查更新");

                    }
                });
            }
        });
    }


    public int getLocalVersion() {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = this.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


}
