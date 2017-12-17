package com.joshua.experiment.activity.answer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.ask.AskQuestionActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.joshua.OrderType;
import com.joshua.experiment.entity.joshua.QuesAnsClassify;
import com.joshua.experiment.pay.util.PaySuccess;
import com.joshua.experiment.pay.util.PayUtils;
import com.joshua.experiment.utils.AudioRecoderUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class QuestionDetailActivity extends BaseActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    @BindView(R.id.question_audit_questioner)
    TextView question_audit_questioner;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.question_audit_content)
    TextView question_audit_content;
    @BindView(R.id.question_audit_listen_count)
    TextView question_audit_listen_count;
    @BindView(R.id.question_audit_time)
    TextView question_audit_time;
    @BindView(R.id.question_audit_duration)
    TextView question_audit_duration;
    //按钮
    @BindView(R.id.question_audit_pay)
    ImageButton question_audit_pay;
    @BindView(R.id.question_audit_read_answer)
    ImageButton question_audit_read_answer;
    //工匠头像
    @BindView(R.id.question_audit_photo)
    ImageView question_audit_photo;
    @BindView(R.id.question_audit_crafts_name)
    TextView question_audit_crafts_name;
    @BindView(R.id.question_audit_crafts_info)
    TextView question_audit_crafts_info;
    @BindView(R.id.question_audit_ask)
    ImageButton question_audit_ask;
    @BindView(R.id.question_audit_tool_bar)
    Toolbar question_audit_tool_bar;

    private QuesAnsClassify mClassify;

    private OkHttpClient mOkHttpClient;
    private ProgressDialog mDialog;
    private Call mCall;
    private static MediaPlayer music;
    private File mFile;
    private boolean isFinishPlaying = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            mDialog.setProgress(progress);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_audit_29);
        ButterKnife.bind(this);
        initIntent();
        mOkHttpClient = new OkHttpClient();
        music = new MediaPlayer();
        music.setOnCompletionListener(this);
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);

        question_audit_tool_bar.setTitle("");
        setSupportActionBar(question_audit_tool_bar);
    }

    private void initIntent() {
        mClassify = (QuesAnsClassify) getIntent().getSerializableExtra("content");
        initData(mClassify);

    }

    private void initData(QuesAnsClassify classify) {
        question_audit_questioner.setText(classify.getUserId());
        tv_money.setText(classify.getMoney() + "元");
        question_audit_content.setText(classify.getQuestionWord());
        question_audit_listen_count.setText(classify.getListenNumber() + "人听过");
        if (classify.getAnsterTime() == null || classify.getAnsterTime().equals("null")) {
            question_audit_time.setText("回答时间：" + "无");
        } else {
            question_audit_time.setText("回答时间：" + classify.getAnsterTime());
        }
        if (classify.getVedioTimes() == null || classify.getVedioTimes().equals("null")) {
            question_audit_duration.setText("答案时长：" + "无");
        } else {
            question_audit_duration.setText("答案时长：" + classify.getVedioTimes());
        }
        if (classify.getIsPay().equals("true")) {
            question_audit_read_answer.setVisibility(View.VISIBLE);
            question_audit_pay.setVisibility(View.GONE);
        } else {
            question_audit_read_answer.setVisibility(View.GONE);
            question_audit_pay.setVisibility(View.VISIBLE);
        }
        Glide.with(this).load(classify.getCraftsImage()).into(question_audit_photo);
        question_audit_crafts_name.setText(classify.getCraftsmanName());
        question_audit_crafts_info.setText(classify.getIntroduction());
        question_audit_ask.setOnClickListener(this);
        question_audit_read_answer.setOnClickListener(this);//已支付
        question_audit_pay.setOnClickListener(this);//未支付


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_audit_read_answer:
                String id = mClassify.getId();
                String url = mClassify.getAnswerAmr();
                //首先判断是否已经下载
                if (checkLocal(id)) {
                    //录音的播放与暂停
                    playRecord();
                } else {
                    mDialog.setMessage("音频下载中，请稍后");
                    mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mCall.cancel();
                            mFile.delete();
                        }
                    });
                    mDialog.show();
                    getSoundFromServer(id, url);
                }
                break;
            case R.id.question_audit_ask:
                Intent intent = new Intent(this, AskQuestionActivity.class);
                intent.putExtra("answer", mClassify.getCraftsmanName());
                intent.putExtra("craftsAccount", mClassify.getCraftsmanId());
                startActivity(intent);
                break;
            case R.id.question_audit_pay:
                PayUtils payUtils = new PayUtils(mBaseActivity);
                payUtils.setPaySuccess(new PaySuccess() {
                    @Override
                    public void onSuccess(String orderNo) {
                        question_audit_read_answer.setVisibility(View.VISIBLE);
                        question_audit_pay.setVisibility(View.GONE);
                    }
                });
                payUtils.payV2(OrderType.TYPE_BYE_MEDIA, mClassify.getId(), 1);
                break;


        }

    }

    /**
     * 判断音频文件是否存在
     */
    private boolean checkLocal(String id) {
        mFile = new File(AudioRecoderUtils.RECODE_PATH, id + ".acc");
        return mFile.exists();
    }

    /**
     * 录音播放
     */
    private void playRecord() {
        if (mFile.exists()) {
            if (music.isPlaying()) {
                music.pause();
            } else {
                try {
                    if (isFinishPlaying) {
                        music = new MediaPlayer();
                        music.setDataSource(mFile.getAbsolutePath());
                        music.prepare();
                        isFinishPlaying = false;
                    }
                    music.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(this, "出现异常，请反馈", Toast.LENGTH_SHORT).show();
        }
    }

    //访问网络，获取音频流，下载，准备播放
    private void getSoundFromServer(final String id, final String url) {
//        RequestBody params = new FormBody.Builder()
//                .add("method", Server.QUERY_QUESTION)
//                .add("Id", id)
//                .build();
        Request request = new Request.Builder()
//                .post(params)
                .url(url)
                .build();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "下载失败", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        mFile.delete();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(AudioRecoderUtils.RECODE_PATH, id + ".acc");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.d("h_bl", "progress=" + progress);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.arg1 = progress;
                        mHandler.sendMessage(msg);
                    }
                    fos.flush();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "下载失败", Toast.LENGTH_SHORT).show();
                            mFile.delete();
                            mDialog.dismiss();
                        }
                    });

                } finally {
                    if (is != null)
                        is.close();
                    if (fos != null)
                        fos.close();
                }
            }
        });

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isFinishPlaying = true;
    }
}
