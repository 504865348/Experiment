package com.joshua.experiment.fragment.albumHome;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.albumHome.AlbumHomeActivity;
import com.joshua.experiment.activity.record.PlayerFrameActivity;
import com.joshua.experiment.adapter.albumhome.AlbumProgramAdapter;
import com.joshua.experiment.entity.AlbumHomePro;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
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

/**
 * Created by nzz on 2017/6/20.
 * 专辑主页-节目Fragment
 */

public class AlbumProgramFragment extends BaseFragment {


    @BindView(R.id.album_program_download)
    ImageView albumProgramDownload;
    @BindView(R.id.album_pro_rv)
    RecyclerView albumProRv;
    @BindView(R.id.album_pro_number)
    TextView albumProNumber;

    private List<AlbumHomePro> list_albumPro;
    private String albumId = AlbumHomeActivity.homeAlbumId;
    private Call mCall;
    private File mFile;
    private OkHttpClient mOkHttpClient;


    private ProgressDialog mDialog;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress=msg.arg1;
            mDialog.setProgress(progress);
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.album_home_program, null);
        mOkHttpClient = new OkHttpClient();
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        list_albumPro = new ArrayList<>();
        getDataFromServer();
    }

    private void getDataFromServer() {
        getAlbumPro();
    }

    private void getAlbumPro() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getActivity()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.PRO_LIST_BY_NAME)
                .add("albumId", albumId)
                .build();
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(getActivity()) {
            @Override
            protected void success(String result) {
                parseAlbumPro(result);
            }

            @Override
            protected void error() {
            }
        });

    }

    private void parseAlbumPro(String result) {
        Gson gson = new Gson();
        list_albumPro = gson.fromJson(result, new TypeToken<List<AlbumHomePro>>() {
        }.getType());
        if(list_albumPro.get(0).getRecordTitle().equals("null")) {
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
                    initLayout();
                }
            });
        }
    }

    private void initLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        albumProRv.setLayoutManager(linearLayoutManager);
        albumProNumber.setText(String.valueOf(linearLayoutManager.getItemCount()));
        AlbumProgramAdapter adapter = new AlbumProgramAdapter(getActivity(), list_albumPro);
        adapter.setOnRecyclerViewItemClickListener(new AlbumProgramAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String position) {
                int pos = Integer.parseInt(position);
                String id = list_albumPro.get(pos).getId();
                String url=list_albumPro.get(pos).getDownloadUrl();
                String title = list_albumPro.get(pos).getRecordTitle();
                //首先判断是否已经下载
                if (checkLocal(id)) {
                    //录音的播放与暂停
                    mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
                    Intent intent=new Intent(mContext,PlayerFrameActivity.class);
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
        albumProRv.setAdapter(adapter);
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
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                            //录音的播放与暂停
                            mFile = new File(AudioRecoderUtils.VIDEO_PATH, "video_"+id + ".mp4");
                            Intent intent=new Intent(mContext,PlayerFrameActivity.class);
                            intent.putExtra("url",mFile.getAbsolutePath());
                            intent.putExtra("title",title);
                            startActivity(intent);
                        }
                    });

                } catch (Exception e) {
                    Log.d(TAG, "onFailure: "+e.getMessage());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void setEmptyView(Boolean isEmpty) {
        FrameLayout empty= (FrameLayout) getActivity().findViewById(R.id.empty_layout);
        if(isEmpty){
            empty.setVisibility(View.VISIBLE);
        }else {
            empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataFromServer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
