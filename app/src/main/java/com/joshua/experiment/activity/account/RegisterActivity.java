package com.joshua.experiment.activity.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.utils.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_sms)
    EditText et_sms;
    @BindView(R.id.btn_send_sms)
    Button btn_send_sms;
    @BindView(R.id.btn_register)
    Button btn_register;
    @BindView(R.id.tv_err)
    TextView tv_err;
    @BindView(R.id.cb_agree)
    CheckBox cb_agree;
    @BindView(R.id.register_tool_bar)
    Toolbar mToolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ll_container)
    LinearLayout ll_container;
    @BindView(R.id.tv_protocol) TextView tv_protocol;

    private String username = "";
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        initListener();
        mCode = MyUtils.generifyCode();
    }

    private void initListener() {
        btn_send_sms.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        tv_protocol.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_sms:
                username = et_username.getText().toString();
                if (MyUtils.isMobileNO(username)) {
                    sendCheckCode();
                } else {
                    showError("手机号码错误");
                }
                break;
            case R.id.btn_register:
                register();
                break;
            case R.id.tv_protocol:
                goToProtocol();
                break;
        }
    }

    private void goToProtocol() {
        startActivity(new Intent(this,ProtocolActivity.class));
    }

    private void register() {
        if (cb_agree.isChecked()) {
            if (mCode.equals(et_sms.getText().toString())) {
                String password = et_password.getText().toString();
                if (!password.isEmpty() && password.length() >= 6 && password.length() <= 20) {
                    showLoadingProgress();
                    OkHttpClient mClient = new OkHttpClient.Builder()
                            .cookieJar(new HttpCookieJar(getApplicationContext()))
                            .build();
                    RequestBody params = new FormBody.Builder()
                            .add("method", Server.SERVER_REGISTER)
                            .add("username", username)
                            .add("password", password)
                            .add("type", "normal")
                            .add("classify","null")
                            .build();

                    final Request request = new Request.Builder()
                            .url(Server.SERVER_REMOTE)
                            .post(params)
                            .build();
                    Call call = mClient.newCall(request);
                    call.enqueue(new HttpCommonCallback(this) {
                        @Override
                        protected void success(String result) {
                            Log.d(TAG, "success: " + result);
                            if (result.equals("true")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissLoadingProgress();
                                        Toast.makeText(mBaseActivity, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                startActivity(new Intent(mBaseActivity, LoginActivity.class));
                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissLoadingProgress();
                                        showError("注册失败");
                                    }
                                });

                            }
                        }

                        @Override
                        protected void error() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissLoadingProgress();
                                    showError("注册失败");
                                }
                            });
                        }
                    });
                } else {
                    showError("密码需要6-20位");
                }

            } else {
                showError("验证码错误");
            }
        } else {
            showError("请阅读并同意工匠注册协议");
        }

    }

    /**
     * 发送短信验证码
     */
    private void sendCheckCode() {

        username = et_username.getText().toString();
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getApplicationContext()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_SMS)
                .add("tel", username)
                .add("code", mCode)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "验证码发送失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseJson = response.body().string();
                Log.d(TAG, "onResponse: "+responseJson);
                try {
                    JSONObject jo = new JSONObject(responseJson);
                    String result = jo.getString("result");
                    if (result.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(mBaseActivity, "验证码发送成功", Toast.LENGTH_SHORT).show();
                                TimeCount time = new TimeCount(60000, 1000);
                                time.start();
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "验证码发送失败，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发送验证码btn显示倒计时
     */
    private class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            btn_send_sms.setText("重新发送");
            btn_send_sms.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            btn_send_sms.setClickable(false);
            btn_send_sms.setText(millisUntilFinished / 1000 + "");
        }
    }

    /**
     * 显示错误
     *
     * @param err 错误信息
     */
    private void showError(String err) {
        tv_err.setVisibility(View.VISIBLE);
        tv_err.setText(err);
    }

    // 监听返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLoadingProgress() {
        progressBar.setVisibility(View.VISIBLE);
        ll_container.setVisibility(View.INVISIBLE);

    }

    public void dismissLoadingProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        ll_container.setVisibility(View.VISIBLE);
    }
}
