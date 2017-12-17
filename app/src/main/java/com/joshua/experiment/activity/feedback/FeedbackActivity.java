package com.joshua.experiment.activity.feedback;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FeedbackActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.feedback_tool_bar)
    Toolbar feedbackToolBar;
    @BindView(R.id.feedback_commit)
    Button feedbackCommit;
    @BindView(R.id.feedback_edit_content)
    EditText edit_content;

    private String editContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        ButterKnife.bind(this);
        initListener();
        feedbackToolBar.setTitle("");
        setSupportActionBar(feedbackToolBar);
    }

    private void initListener() {
        feedbackCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_commit:
                putDataToServer();
                break;
        }
    }

    private void putDataToServer() {
        editContent = edit_content.getText().toString();
        if (editContent.isEmpty()) {
            Toast.makeText(mBaseActivity, "请您填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            putFeedBack(editContent);
        }
    }

    private void putFeedBack(String keyWord) {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        String requestStr = "method=" + Server.FEEDBACK + "&keyWord=" + keyWord;
        RequestBody params = RequestBody.create(Server.MEDIA_TYPE_MARKDOWN,requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(mBaseActivity) {
            @Override
            protected void success(String result) {
                Log.d(TAG, "success: " + result);
                if (result.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "意见反馈成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "意见反馈失败,请检查网络连接", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            protected void error() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "意见反馈失败,请检查网络连接", Toast.LENGTH_SHORT).show();

                    }
                });
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