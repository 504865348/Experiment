package com.joshua.experiment.activity.core;

import android.os.Environment;
import android.os.Bundle;
import android.util.Log;


import com.joshua.experiment.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestActivity extends BaseActivity {

    private OkHttpClient mOkHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mOkHttpClient = new OkHttpClient();
//        upLoad();
        downLoad();

    }

    public void downLoad() {
        Request request = new Request.Builder()
                .url("http://139.224.35.126:8080/GJ/WEB-INF/upload/10/1/318a9cbc-c326-4776-a9fb-88661f453681_portrait.JPEG")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "failure download!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XHJ/18761996926/";
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, "test.JPEG");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
//                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
//                    listener.onDownloadSuccess();
                } catch (Exception e) {
//                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });

    }

    public void upLoad() {

        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/XHJ/18761996926/portrait.JPEG";
        Log.d(TAG, "upLoad: " + filepath);
        File file = new File(filepath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "portrait.JPEG", fileBody)
                .build();
        Request request = new Request.Builder()
                .url("http://139.224.35.126:8080/GJ/UploadServlet")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "failure upload!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i(TAG, "success upload!");
            }
        });
    }
}
