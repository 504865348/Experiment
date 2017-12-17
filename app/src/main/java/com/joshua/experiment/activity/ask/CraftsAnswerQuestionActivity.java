package com.joshua.experiment.activity.ask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.other.ShowPhotoActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.utils.AudioRecoderUtils;
import com.joshua.experiment.utils.PopupWindowFactory;
import com.joshua.experiment.utils.PrefUtils;
import com.joshua.experiment.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.widget.Toast.makeText;


public class CraftsAnswerQuestionActivity extends BaseActivity implements MediaPlayer.OnCompletionListener {
    static final int VOICE_REQUEST_CODE = 66;

    private ImageView mImageView;
    private TextView mTextView;
    private AudioRecoderUtils mAudioRecoderUtils;
    private Context context;
    private PopupWindowFactory mPop;
    private LinearLayout ll;

    @BindView(R.id.answer_question_recording)
    Button mButton;
    @BindView(R.id.tv_question)
    TextView tv_question;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_ques)
    ImageView iv_ques;

    private String mFilePath = "";
    private static MediaPlayer music;
    private long mRecordTime;
    private String mId;
    private String mQuestion,mPic;
    private Call mCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_question_44);
        ButterKnife.bind(this);
        initData();
        final View view = View.inflate(this, R.layout.layout_microphone, null);
        mPop = new PopupWindowFactory(this, view);
        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        context = this;
        ll = (LinearLayout) findViewById(R.id.ll);
        mAudioRecoderUtils = new AudioRecoderUtils();
        music = new MediaPlayer();
        music.setOnCompletionListener(this);
        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(TimeUtils.long2String(time));
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                makeText(mBaseActivity, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                mTextView.setText(TimeUtils.long2String(0));
            }
        });
        StartListener();
        Glide.with(this).load(mPic).error(R.drawable.load_error).into(iv_ques);
        //6.0以上需要权限申请
//        requestPermissions();


        iv_ques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mBaseActivity, ShowPhotoActivity.class);
                intent.putExtra("pic",mPic);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mId = getIntent().getStringExtra("Id");
        mQuestion = getIntent().getStringExtra("question");
        tv_question.setText("问题内容：" + mQuestion);
        mPic=getIntent().getStringExtra("pic");
        Log.d(TAG, "initData: "+mPic);
    }

    private boolean isFinishRecording = false;

    public void StartListener() {
        mButton.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFinishRecording) {
//                          Toast.makeText(mBaseActivity, "录制已完成，若需更改，请点击重录", Toast.LENGTH_SHORT).show();
                    message.setTextColor(Color.RED);
                    message.setText("录制已完成，若需更改，请点击重录");

                } else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mPop.showAtLocation(ll, Gravity.CENTER, 0, 0);
                            mAudioRecoderUtils.startRecord();
                            mFilePath = mAudioRecoderUtils.getFilePath();
                            break;

                        case MotionEvent.ACTION_UP:
                            //结束录音（保存录音文件）
                            mRecordTime = mAudioRecoderUtils.stopRecord() / 1000;
                            Log.d(TAG, "record time: " + mRecordTime);
                            mPop.dismiss();
                            isFinishRecording = true;
                            break;
                    }
                }

                return true;
            }
        });
    }

    private boolean isFinishPlaying = true;

    /**
     * 录音试听
     */
    @OnClick(R.id.answer_question_listen)
    public void listener() {
        if (!mFilePath.isEmpty()) {
            if (music.isPlaying()) {
                music.pause();
            } else {
                try {
//                    if (isFinishPlaying) {
                        music = new MediaPlayer();
                        music.setDataSource(mFilePath);
                        Log.d(TAG, "<<<<<<<<<<<<listener>>>>>>>>>>>>: "+mFilePath);
                        music.prepare();
                        isFinishPlaying = false;
//                    }
                    music.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            makeText(mBaseActivity, "请先录制", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isFinishPlaying = true;
    }

    /**
     * 录音重录
     */
    @OnClick(R.id.answer_question_rerecord)
    public void reRecord() {
        if (mFilePath.isEmpty()) {
            makeText(mBaseActivity, "请先录制", Toast.LENGTH_SHORT).show();
        } else {
            File file = new File(mFilePath);
            file.delete();
            message.setTextColor(Color.BLACK);
            message.setText("长按进行录制，最长两分钟录制");
            makeText(mBaseActivity, "音频删除成功，请重新录制", Toast.LENGTH_SHORT).show();
            mFilePath = "";
            isFinishRecording = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mCall!=null){
            mCall.cancel();
        }

    }

    /**
     * 录音发送
     */
    @OnClick(R.id.answer_question_send)
    public void sendRecord() {
        if (mFilePath.isEmpty()) {
            makeText(mBaseActivity, "请先录制", Toast.LENGTH_SHORT).show();
        } else {
            showLoadingProgress();
            postToServer();
        }
    }


    private void postToServer() {
        String user = PrefUtils.getString(mBaseActivity, "phone", "");
        File file = new File(mFilePath);

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "answer.aac", fileBody)
                .addFormDataPart("id", mId)
                .addFormDataPart("user", user)
                .addFormDataPart("time", mRecordTime + "")
                .build();
        Request request = new Request.Builder()
                .url(Server.SERVER_VIDEO)
                .post(requestBody)
                .build();
        mCall = new OkHttpClient().newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoadingProgress();
                        makeText(mBaseActivity, "回答失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "onResponse: " + responseJson);
                JSONObject jo = null;
                try {
                    jo = new JSONObject(responseJson);
                    String result = jo.getString("result");
                    if (result.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingProgress();
                                makeText(mBaseActivity, "回答成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissLoadingProgress();
                                makeText(mBaseActivity, "回答失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        music.release();
    }
    //    /**
//     * 开启扫描之前判断权限是否打开
//     */
//    private void requestPermissions() {
//        //判断是否开启摄像头权限
//        if ((ContextCompat.checkSelfPermission(context,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
//                (ContextCompat.checkSelfPermission(context,
//                        Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
//                ) {
//            StartListener();
//
//            //判断是否开启语音权限
//        } else {
//            //请求获取摄像头权限
//            ActivityCompat.requestPermissions((Activity) context,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, VOICE_REQUEST_CODE);
//        }
//
//    }

//    /**
//     * 请求权限回调
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == VOICE_REQUEST_CODE) {
//            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
//                StartListener();
//            } else {
//                Toast.makeText(context, "已拒绝权限！", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
public void showLoadingProgress() {
    progressBar.setVisibility(View.VISIBLE);
    ll.setVisibility(View.INVISIBLE);

}

    public void dismissLoadingProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.VISIBLE);
    }
}
