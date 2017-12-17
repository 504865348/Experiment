package com.joshua.experiment.activity.record;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.utils.PrefUtils;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.util.Auth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.joshua.experiment.R.array.cost;

public class PostRecordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.record_info_title)
    EditText record_info_title;
    @BindView(R.id.record_info_choose_album)
    TextView record_info_choose_album;
    @BindView(R.id.record_info_price)
    Spinner record_info_price;
    @BindView(R.id.record_info_intro)
    EditText record_info_intro;
    @BindView(R.id.record_info_tool_bar)
    Toolbar recordInfoToolBar;

    private String mAlbumId;
    private String name;
    private ProgressDialog mDialog;
    private long mDuration;
    private String mCost = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_info);
        ButterKnife.bind(this);
        record_info_choose_album.setOnClickListener(this);
        name = getIntent().getStringExtra("name");

        Button btn_post = (Button) findViewById(R.id.btn_post);
        btn_post.setOnClickListener(this);
        recordInfoToolBar.setOnClickListener(this);
        record_info_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] costs = getResources().getStringArray(cost);
                mCost = costs[position];
                Log.d("cost", "onItemSelected: " + mCost);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * 上传
     * 2017-11-28修改
     * 上传至原服务器，改为上传至七牛云，方法废弃
     */
    @Deprecated
    public void postToServer() {
        String title = record_info_title.getText().toString();
        String idAlbum = mAlbumId;
        String creater = PrefUtils.getString(mBaseActivity, "phone", "");
        String intro = record_info_intro.getText().toString();
        String path;
        if (name == null) {
            path = getIntent().getStringExtra("videoPath");
        } else {
            path = Environment.getExternalStorageDirectory().getPath() + "/crafts_videos/" + name;
        }
        File file = new File(path);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mDuration = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("record", "record.mp4", fileBody)
                .addFormDataPart("RecordTitle", title)
                .addFormDataPart("Name", creater)
                .addFormDataPart("Special", idAlbum)
                .addFormDataPart("Introduction", intro)
                .addFormDataPart("Duration", mDuration + "")
                .addFormDataPart("money", mCost)
                .build();
        Request request = new Request.Builder()
                .url(Server.SERVER_RECORD)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "createAlbum:fail" + e.getMessage());
                mDialog.dismiss();
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
                                PrefUtils.setString(mBaseActivity, name, name);
                                Toast.makeText(mBaseActivity, "节目添加成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "节目添加失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    mDialog.dismiss();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_info_tool_bar:
                finish();
                break;
            case R.id.record_info_choose_album:
                Intent intent = new Intent(this, AlbumListActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_post:
                if(record_info_title.getText().toString().isEmpty()||
                        mAlbumId==null|| mAlbumId.isEmpty()|| record_info_intro.getText().toString().isEmpty()){
                    Toast.makeText(mBaseActivity,"请填写完整信息",Toast.LENGTH_SHORT).show();
                    return;
                }
                mDialog = new ProgressDialog(this);
                mDialog.setCancelable(false);
                mDialog.setMax(100);
                mDialog.setProgress(0);
                mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDialog.setMessage("视频上传中，请稍后...");
                mDialog.show();
                //将视频上传至七牛云并且返回服务器地址
                postToQNY();
                break;
        }
    }


    private void postToQNY() {
        String accessKey = "q6OOt03fP_CJS6nSXW3OZeNSSoDiNRpLXYthiN5c";
        String secretKey = "HJQO2BjtP3dRM1k90e0YTlhkzogxRcbiQII0vmZN";
        String bucket = "buildoneqncloud";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();
        UploadManager uploadManager = new UploadManager(config);
        String path;
        if (name == null) {
            path = getIntent().getStringExtra("videoPath");
            String fileName=new File(path).getName();

            path=Environment.getExternalStorageDirectory().getPath() + "/crafts_videos/" + fileName;
        } else {
            path = Environment.getExternalStorageDirectory().getPath() + "/crafts_videos/" + name;
        }


        File mediaStorageDir = new File(path);

        String key = name==null?mediaStorageDir.getName():name;
        Log.i("qiniu", "mediaStorageDir:"+mediaStorageDir.getAbsolutePath()+"\n"
        +"key:"+key+"\n"
                +"upTpken:"+upToken);
        uploadManager.put(mediaStorageDir, key, upToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        if (info.isOK()) {
                            Log.d("qiniu", "Upload Success");
                            postInfoToServer();
                        } else {
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            mDialog.dismiss();
                            Toast.makeText(mBaseActivity,"节目添加失败",Toast.LENGTH_SHORT).show();

                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                },
                //上传进度
                new UploadOptions(null, null, false,
                        new UpProgressHandler() {
                            public void progress(String key, double percent) {
                                mDialog.setProgress((int)(percent*100));
                                Log.i("qiniu", key + ": " + (int)(percent*100));
                            }
                        }, null));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                String title = data.getStringExtra("albumTitle");
                mAlbumId = data.getStringExtra("albumId");
                record_info_choose_album.setText(title);
                break;
            default:
                break;
        }


    }

    /**
     * 2017-11-28新增
     * 将七牛云上传结果发送给服务器
     */
    public void postInfoToServer() {
        String title = record_info_title.getText().toString();
        String idAlbum = mAlbumId;
        String creater = PrefUtils.getString(mBaseActivity, "phone", "");
        String intro = record_info_intro.getText().toString();
        String path;
        if (name == null) {
            path = getIntent().getStringExtra("videoPath");
        } else {
            path = Environment.getExternalStorageDirectory().getPath() + "/crafts_videos/" + name;
        }
        File file = new File(path);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file.getAbsolutePath());
            mDuration = mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(getApplicationContext()))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.SERVER_POST_RECORD)
                .add("RecordTitle", title)
                .add("Name", creater)
                .add("Special", idAlbum)
                .add("Introduction", intro)
                .add("Duration", mDuration + "")
                .add("money", mCost)
                .add("name", name)
                .build();

        Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "createAlbum:fail" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "onResponse:wtf? " + responseJson);
                JSONObject jo = null;
                try {
                    jo = new JSONObject(responseJson);
                    String result = jo.getString("result");
                    if (result.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PrefUtils.setString(mBaseActivity, name, name);
                                Toast.makeText(mBaseActivity, "节目添加成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "节目添加失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    });
                }

            }
        });
    }


}

