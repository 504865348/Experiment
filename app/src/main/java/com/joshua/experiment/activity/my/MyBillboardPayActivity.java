package com.joshua.experiment.activity.my;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.record.PlayerFrameActivity;
import com.joshua.experiment.adapter.my.MyBillboardPayAdapter;
import com.joshua.experiment.entity.MyBillboardPay;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.utils.AudioRecoderUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyBillboardPayActivity extends BaseActivity {

    @BindView(R.id.my_billboard_pay_rv)
    RecyclerView myBillboardPayRv;

    private List<MyBillboardPay> list;

    private Call mCall;
    private File mFile;
    private OkHttpClient mOkHttpClient;
    private ProgressDialog mDialog;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress=msg.arg1;
            mDialog.setProgress(progress);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_billboard_pay);
        ButterKnife.bind(this);
        mOkHttpClient = new OkHttpClient();
        initData();
        initProDialogRefresh();
    }

    private void initProDialogRefresh() {
        mDialog = new ProgressDialog(mBaseActivity);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void initData() {
        list = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {

        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.MY_BILLBOARD_PAY)
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
                parseData(result);
            }

            @Override
            protected void error() {

            }
        });

    }

    private void parseData(String result) {
        Gson gson = new Gson();
        list = gson.fromJson(result, new TypeToken<List<MyBillboardPay>>() {
        }.getType());
        if (list.get(0).getRecordTitle().equals("null")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false);
                    initRecycle();
                }
            });
        }
    }

    private void initRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myBillboardPayRv.setLayoutManager(linearLayoutManager);
        MyBillboardPayAdapter adapter = new MyBillboardPayAdapter(this, list);
        adapter.setOnRecyclerViewItemClickListener(new MyBillboardPayAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                String id = list.get(pos).getId();
                String url = list.get(pos).getDownloadUrl();
                String title = list.get(pos).getRecordTitle();
                //首先判断是否已经下载
                if (checkLocal(id)) {
                    //录音的播放与暂停
                    mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
                    Intent intent=new Intent(mBaseActivity,PlayerFrameActivity.class);
                    intent.putExtra("url",mFile.getAbsolutePath());
                    intent.putExtra("title",title);
                    startActivity(intent);
                } else {
                    mDialog.setMessage("视频下载中，请稍后");
                    mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mFile.delete();
                            mCall.cancel();
                        }
                    });
                    mDialog.show();
                    getSoundFromServer(id,url,title);
                }
            }
        });
        myBillboardPayRv.setAdapter(adapter);
    }

    /**
     * 判断音频文件是否存在
     */
    private boolean checkLocal(String id) {
        mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
        return mFile.exists();
    }

    //访问网络，获取音频流，下载，准备播放
    private void getSoundFromServer(final String id,final String url,final String title) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mCall = mOkHttpClient.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
                mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "下载失败", Toast.LENGTH_SHORT).show();
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
                    //首先要创建文件夹
                    File path = new File(AudioRecoderUtils.VIDEO_PATH);
                    if (!path.exists())
                        path.mkdirs();
                    File file = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
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
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            //录音的播放与暂停
                            mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
                            Intent intent=new Intent(mBaseActivity,PlayerFrameActivity.class);
                            intent.putExtra("url",mFile.getAbsolutePath());
                            intent.putExtra("title",title);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    Log.d(TAG, "onFailure: "+e.getMessage());
                    mBaseActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mBaseActivity, "下载失败", Toast.LENGTH_SHORT).show();
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

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) mBaseActivity.findViewById(R.id.empty);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }
}
