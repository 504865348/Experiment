package com.joshua.experiment.activity.record;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.utils.MyUtils;
import com.joshua.experiment.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateAlbumActivity extends BaseActivity {
    @BindView(R.id.iv_add_pic)
    ImageView add;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.Spinner01)
    Spinner spinner;
    @BindView(R.id.Spinner02)
    Spinner spinnerModels;
    @BindView(R.id.et_introduction)
    EditText etIntroduction;
    private String[] mTypes, mModels;
    private String mType, mModel;

    private PopupWindow pop = null;
    private View parentView;
    private LinearLayout ll_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_new_album);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mType = mTypes[pos];
                Log.d(TAG, "onItemSelected: " + mType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerModels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mModel = mModels[pos];
                Log.d(TAG, "onItemModelSelected: " + mModel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //加载父布局
        parentView = getLayoutInflater().inflate(R.layout.activity_ask_question, null);
        InitPopWindow();
    }

    private void initListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
            }
        });

    }

    private void initData() {
        mTypes = getResources().getStringArray(R.array.albumTypes);
        mType = mTypes[0];
        mModels = getResources().getStringArray(R.array.albumModels);
        mModel = mModels[0];
    }


    /**
     * 初始化pop
     */
    private static final int CHOOSE_PICTURE = 0x000001;
    private static final int TAKE_PICTURE = 0x000002;

    private void InitPopWindow() {
        pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }


    /**
     * 返回图片
     */
    private static final String IMAGE_FILE_NAME = "album";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //保存为头像文件
                    MyUtils.saveBitmap(bitmap
                            , PrefUtils.getString(mBaseActivity, "phone", ""), IMAGE_FILE_NAME);
                    add.setImageBitmap(bitmap);
                }
                break;
            case CHOOSE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    //获取图片路径并保存为Bitmap
                    Bitmap bitmap = MyUtils.getBitmapFromUri(this, uri);
                    //保存为头像文件
                    MyUtils.saveBitmap(bitmap
                            , PrefUtils.getString(mBaseActivity, "phone", ""), IMAGE_FILE_NAME);
                    add.setImageBitmap(bitmap);
                }
                break;
        }
    }

    // 监听返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void createAlbum(View view) {
        String specialName = et_title.getText().toString();
        String introduction = etIntroduction.getText().toString();
        String typeName = mType;
        String modelName = mModel;
        String creater = PrefUtils.getString(mBaseActivity, "phone", "");
        String absPath = Environment.getExternalStorageDirectory() + "/craftsman/" + PrefUtils.getString(mBaseActivity, "phone", "");
        File file = new File(absPath, IMAGE_FILE_NAME + ".JPEG");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "album.JPEG", fileBody)
                .addFormDataPart("specialName", specialName)
                .addFormDataPart("typeName", typeName)
                .addFormDataPart("creater", creater)
                .addFormDataPart("introduction", introduction)
                .addFormDataPart("modelName", modelName)
                .build();
        Request request = new Request.Builder()
                .url(Server.SERVER_ALBUM)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "createAlbum:fail" + e.getMessage());
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
                                Toast.makeText(mBaseActivity, "专辑添加成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "专辑添加失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
