package com.joshua.experiment.activity.set;

import android.content.Intent;
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
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class EditPswActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.etOldPsw)
    EditText et_OldPsw;
    @BindView(R.id.etNewPsw)
    EditText et_NewPsw;
    @BindView(R.id.etEnsurePsw)
    EditText et_EnsurePsw;
    @BindView(R.id.psw_commit)
    Button pswCommit;
    @BindView(R.id.edit_psw_tool_bar)
    Toolbar edit_psw_tool_bar;

    public String editOldPsw, editNewPsw, editEnsurePsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_edit_psw);
        ButterKnife.bind(this);
        edit_psw_tool_bar.setTitle("");
        setSupportActionBar(edit_psw_tool_bar);
        initListener();
    }

    private void initListener() {
        pswCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.psw_commit:
                putDataToServer();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void putDataToServer() {
        editOldPsw = et_OldPsw.getText().toString();
        editNewPsw = et_NewPsw.getText().toString();
        editEnsurePsw = et_EnsurePsw.getText().toString();
        if (editOldPsw.isEmpty())
            Toast.makeText(this, "请输入当前密码！", Toast.LENGTH_SHORT).show();
        else if (editNewPsw.isEmpty())
            Toast.makeText(this, "请输入新密码！", Toast.LENGTH_SHORT).show();
        else if (editNewPsw.length() < 6 || editNewPsw.length() > 20)
            Toast.makeText(this, "新密码必须是6-12位数字或字母！", Toast.LENGTH_SHORT).show();
        else if (editEnsurePsw.isEmpty())
            Toast.makeText(this, "请确认输入的新密码！", Toast.LENGTH_SHORT).show();
        else if (!editNewPsw.equals(editEnsurePsw))
            Toast.makeText(this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
        else {
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .cookieJar(new HttpCookieJar(getApplicationContext()))
                    .build();
            RequestBody params = new FormBody.Builder()
                    .add("method", Server.EDIT_PSW)
                    .add("oldPsw", editOldPsw)
                    .add("newPsw", editNewPsw)
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
                                Toast.makeText(EditPswActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(EditPswActivity.this, SetActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditPswActivity.this, "修改密码失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                protected void error() {
                    Toast.makeText(EditPswActivity.this, "修改密码失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
