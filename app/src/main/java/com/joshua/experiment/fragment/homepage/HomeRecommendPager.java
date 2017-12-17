package com.joshua.experiment.fragment.homepage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.record.PlayerFrameActivity;
import com.joshua.experiment.adapter.HomeRecommendAdapter;
import com.joshua.experiment.application.BaseApplication;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.OrderType;
import com.joshua.experiment.entity.joshua.VideoDetail;
import com.joshua.experiment.fragment.BaseFragment;
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


public class HomeRecommendPager extends BaseFragment {


    @BindView(R.id.home_recommend_rv)

    RecyclerView home_recommend_rv;

    private List<VideoDetail> list_TJ;

    private Call mCall;
    private File mFile;
    private OkHttpClient mOkHttpClient;


    private ProgressDialog mDialog;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            mDialog.setProgress(progress);
        }
    };
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public HomeRecommendPager() {
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.home_page_recommend, null);
        mOkHttpClient = new OkHttpClient();
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        initRefreshRecycleView(view);
        return view;
    }

    private void initRefreshRecycleView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }
        });
    }

    @Override
    public void initData() {
        Log.d(TAG, "initData: ");
        list_TJ = new ArrayList<>();
        getDataFromServer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getDataFromServer() {
        getTJ();//推荐
    }

    private void getTJ() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.BILLBOARD_HOT)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                Log.d("tuijian", "success: "+result);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });

                parseTJ(result);
            }

            @Override
            protected void error() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }

    private void parseTJ(String result) {
        Gson gson = new Gson();
        list_TJ = gson.fromJson(result, new TypeToken<List<VideoDetail>>() {
        }.getType());
        if (list_TJ.get(0).getRecordTitle().equals("null")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(true);
                }
            });
        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setEmptyView(false);
                    initRecycleTJ();
                }
            });
        }
    }

    private void initRecycleTJ() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        home_recommend_rv.setLayoutManager(linearLayoutManager);
        HomeRecommendAdapter adapter = new HomeRecommendAdapter(getActivity(), list_TJ);
        adapter.setOnRecyclerViewItemClickListener(new HomeRecommendAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                final int pos = Integer.parseInt(position);
                final String id = list_TJ.get(pos).getId();
                final String url = list_TJ.get(pos).getDownloadUrl();
                final String title = list_TJ.get(pos).getRecordTitle();
                String isPay = list_TJ.get(pos).getIsPay();
                PayUtils utils = new PayUtils(getActivity());
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
//                            Intent intent = new Intent(mContext, PlayerFrameActivity.class);
//                            intent.putExtra("url", mFile.getAbsolutePath());
//                            intent.putExtra("title", title);
//                            intent.putExtra("entity", list_TJ.get(pos));
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
                        Intent intent = new Intent(getActivity(), PlayerFrameActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("title", title);
                        intent.putExtra("entity", list_TJ.get(pos));
                        startActivity(intent);
                    }
                });

                if(isPay.equals("true")){
//                    //首先判断是否已经下载
//                    if (checkLocal(id)) {
//                        //录音的播放与暂停
//                        mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
//                        Intent intent = new Intent(mContext, PlayerFrameActivity.class);
//                        intent.putExtra("url", mFile.getAbsolutePath());
//                        intent.putExtra("title", title);
//                        intent.putExtra("entity", list_TJ.get(pos));
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
                    Intent intent = new Intent(getActivity(), PlayerFrameActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("title", title);
                    intent.putExtra("entity", list_TJ.get(pos));
                    startActivity(intent);
                }else{
                    //没有支付跳转支付
                    utils.payV2(OrderType.TYPE_BYE_VIDEO, list_TJ.get(pos).getId(),  Float.parseFloat(list_TJ.get(pos).getMoney()));
                }





            }
        });
        home_recommend_rv.setAdapter(adapter);
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            //录音的播放与暂停
                            mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_" + id + ".mp4");
                            Intent intent = new Intent(mContext, PlayerFrameActivity.class);
                            intent.putExtra("url", mFile.getAbsolutePath());
                            intent.putExtra("title", title);
                            intent.putExtra("entity", list_TJ.get(pos));
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    Log.d(TAG, "onFailure: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
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
        FrameLayout empty = (FrameLayout) getActivity().findViewById(R.id.empty_layout);
        if (isEmpty) {
            empty.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();
    }
}
