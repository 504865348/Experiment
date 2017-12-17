package com.joshua.experiment.activity.my;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.record.PlayerFrameActivity;
import com.joshua.experiment.adapter.HomeRecommendAdapter;
import com.joshua.experiment.application.BaseApplication;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.OrderType;
import com.joshua.experiment.entity.joshua.VideoDetail;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.pay.util.PaySuccess;
import com.joshua.experiment.pay.util.PayUtils;
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

public class MyBillboardHotActivity extends BaseActivity {

    @BindView(R.id.billboard_hot_program_rv)
    RecyclerView billboard_hot_program_rv;

    @BindView(R.id. billboard_hot_tool_bar)
    Toolbar billboard_hot_tool_bar;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private Call mCall;
    private List<VideoDetail> list_collect;
    private OkHttpClient mClient;
    private File mFile;
    private ProgressDialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            mDialog.setProgress(progress);
        }
    };
    private OkHttpClient mOkHttpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billboard_hot_program);
        ButterKnife.bind(this);
        billboard_hot_tool_bar.setTitle("");
        setSupportActionBar(billboard_hot_tool_bar);
        init();
        tv_title.setText("我的榜单");
    }

    private void init() {
        mOkHttpClient = new OkHttpClient();
        list_collect=new ArrayList<>();
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        getDataFromServer();
    }



    private void getDataFromServer() {
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        RequestBody params=new FormBody.Builder()
                .add("method", Server.MY_BILLBOARD_HOT)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(this) {
            @Override
            protected void success(String result) {
                Log.d("collect", "success: "+result);
                parseData(result);
            }

            @Override
            protected void error() {

            }
        });
    }

    private void parseData(String result) {
        Gson gson = new Gson();
        list_collect = gson.fromJson(result, new TypeToken<List<VideoDetail>>() {
        }.getType());
        if (list_collect.get(0).getRecordTitle().equals("null")) {
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
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        billboard_hot_program_rv.setLayoutManager(linearLayoutManager);
        HomeRecommendAdapter adapter = new HomeRecommendAdapter(this, list_collect);
        adapter.setOnRecyclerViewItemClickListener(new HomeRecommendAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                final int pos = Integer.parseInt(position);
                final String id = list_collect.get(pos).getId();
                final String url = list_collect.get(pos).getDownloadUrl();
                final String title = list_collect.get(pos).getRecordTitle();
                String isPay = list_collect.get(pos).getIsPay();
                PayUtils utils = new PayUtils(mBaseActivity);
                utils.setPaySuccess(new PaySuccess() {
                    @Override
                    public void onSuccess(String o) {
                        getDataFromServer();
                        Log.d(TAG, "onSuccess: pay success");
                        //首先判断是否已经下载
                        Toast.makeText(BaseApplication.getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
//                        if (checkLocal(id)) {
//                            //录音的播放与暂停
//                            mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
//                            Intent intent = new Intent(mBaseActivity, PlayerFrameActivity.class);
//                            intent.putExtra("url", mFile.getAbsolutePath());
//                            intent.putExtra("title", title);
//                            intent.putExtra("entity", list_collect.get(pos));
//                            startActivity(intent);
//                        } else {
//                            mDialog.setMessage("视频下载中，请稍后");
//                            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
//                                    mFile.delete();
//                                    mCall.cancel();
//                                }
//                            });
//                            mDialog.show();
//                            getSoundFromServer(id, url, title, pos);
//                        }
                        //取消下载功能，直接在线播放
                        Intent intent = new Intent(mBaseActivity, PlayerFrameActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("title", title);
                        intent.putExtra("entity", list_collect.get(pos));
                        startActivity(intent);
                    }
                });

                if(isPay.equals("true")){
//                    //首先判断是否已经下载
//                    if (checkLocal(id)) {
//                        //录音的播放与暂停
//                        mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
//                        Intent intent = new Intent(mBaseActivity, PlayerFrameActivity.class);
//                        intent.putExtra("url", mFile.getAbsolutePath());
//                        intent.putExtra("title", title);
//                        intent.putExtra("entity", list_collect.get(pos));
//                        startActivity(intent);
//                    } else {
//
//                        mDialog.setMessage("视频下载中，请稍后");
//                        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                mFile.delete();
//                                mCall.cancel();
//                            }
//                        });
//                        mDialog.show();
//                        getSoundFromServer(id, url, title, pos);
//                    }
                    //取消下载功能，直接在线播放
                    Intent intent = new Intent(mBaseActivity, PlayerFrameActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("title", title);
                    intent.putExtra("entity", list_collect.get(pos));
                    startActivity(intent);
                }else{
                    //没有支付跳转支付
                    utils.payV2(OrderType.TYPE_BYE_VIDEO, list_collect.get(pos).getId(), Float.parseFloat(list_collect.get(pos).getMoney()));
                }





            }
        });
        billboard_hot_program_rv.setAdapter(adapter);
    }



    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) findViewById(R.id.empty);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

    /**
     * 判断音频文件是否存在
     */
    private boolean checkLocal(String id) {
        mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
        return mFile.exists();
    }


    //访问网络，获取音频流，下载，准备播放
    private void getSoundFromServer(final String id, final String url, final String title, final int pos) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                runOnUiThread(new Runnable() {
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
                    File file = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
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
                            Toast.makeText(mBaseActivity, "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            //录音的播放与暂停
                            mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
                            Intent intent = new Intent(mBaseActivity, PlayerFrameActivity.class);
                            intent.putExtra("url", mFile.getAbsolutePath());
                            intent.putExtra("title", title);
                            intent.putExtra("entity", list_collect.get(pos));
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    runOnUiThread(new Runnable() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
