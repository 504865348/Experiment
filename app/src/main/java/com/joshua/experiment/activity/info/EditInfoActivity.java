package com.joshua.experiment.activity.info;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.utils.MyUtils;
import com.joshua.experiment.utils.PrefUtils;
import com.joshua.experiment.utils.myinfoCityAndDate.ChooseCityInterface;
import com.joshua.experiment.utils.myinfoCityAndDate.ChooseCityUtil;
import com.joshua.experiment.utils.myinfoCityAndDate.ChooseDateInterface;
import com.joshua.experiment.utils.myinfoCityAndDate.ChooseDateUtil;

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


public class EditInfoActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edit_my_info_tool_bar)
    Toolbar editMyInfoToolBar;
    @BindView(R.id.etNickname)
    EditText et_Nickname;
    @BindView(R.id.etIntroduce)
    EditText et_Introduce;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.my_info_commit)
    Button myInfoCommit;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.iv_my_image)
    ImageView ivMyImage;

    private PopupWindow pop = null;
    private View parentView;
    private LinearLayout ll_popup;
    private String nickName, introduce, sex, birthday, address;
    private SharedPreferences sp;
    private static final int CHOOSE_PICTURE = 0x000001;
    private static final int TAKE_PICTURE = 0x000002;
    private static final String IMAGE_FILE_NAME = "headImage";
    private String userClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_edit_myinfo);
        ButterKnife.bind(this);
        initListener();
        InitPopWindow();
        editMyInfoToolBar.setTitle("");
        setSupportActionBar(editMyInfoToolBar);

        parentView = getLayoutInflater().inflate(R.layout.activity_ask_question, null);

        userClass = PrefUtils.getString(mBaseActivity, "phone", "");
        sp = getSharedPreferences(userClass, Context.MODE_PRIVATE);
        nickName = sp.getString("nickName", "");
        introduce = sp.getString("introduce", "");
        sex = sp.getString("sex", "");
        birthday = sp.getString("birthday", "");
        address = sp.getString("address", "");
showInfo();
    }


    private void initListener() {
        ivMyImage.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        myInfoCommit.setOnClickListener(this);
    }

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
                Intent openHeadImageIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openHeadImageIntent.setType("image/*");
                startActivityForResult(openHeadImageIntent, CHOOSE_PICTURE);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //保存为头像文件
                    MyUtils.saveBitmap(bitmap
                            , PrefUtils.getString(mBaseActivity, "phone", ""), IMAGE_FILE_NAME);
                    ivMyImage.setImageBitmap(bitmap);
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
                    ivMyImage.setImageBitmap(bitmap);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_my_image:
                setHeadImage();
                break;
            case R.id.tvSex:
                setSex();
                break;
            case R.id.tvBirthday:
                setBirthday();
                break;
            case R.id.tvAddress:
                setAddress();
                break;
            case R.id.my_info_commit:
                saveInfo();
                break;
        }
    }

    private void setHeadImage() {
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }

    private void setSex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("设置性别");
        final String[] items = {"男", "女", "保密"};
        int i;
        for (i = 0; i < items.length; i++) {
            if (items[i].equals(tvSex.getText())) {
                break;
            }
        }
        builder.setSingleChoiceItems(items, i, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvSex.setText(items[which].toString());
                sex = tvSex.getText().toString();
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setBirthday() {
        final ChooseDateUtil dateUtil = new ChooseDateUtil();
        int[] oldDateArray = {2017, 01, 01};
        dateUtil.createDialog(this, oldDateArray, new ChooseDateInterface() {
            @Override
            public void sure(int[] newDateArray) {
                tvBirthday.setText(newDateArray[0] + "年" + newDateArray[1] + "月" + newDateArray[2] + "日");
                tvBirthday.setTextColor(getResources().getColor(R.color.black));
                birthday = tvBirthday.getText().toString();
            }
        });

    }

    private void setAddress() {
        final ChooseCityUtil cityUtil = new ChooseCityUtil();
        String[] oldCityArray = {"江苏", "镇江", "京口"};
        cityUtil.createDialog(this, oldCityArray, new ChooseCityInterface() {
            @Override
            public void sure(String[] newCityArray) {
                tvAddress.setText(newCityArray[0] + "-" + newCityArray[1] + "-" + newCityArray[2]);
                address = tvAddress.getText().toString();
            }
        });
    }

    private void saveInfo() {

        nickName = et_Nickname.getText().toString();
        introduce = et_Introduce.getText().toString();


        SharedPreferences.Editor editor = sp.edit();

        if (nickName.isEmpty() || introduce.isEmpty()
                || sex.isEmpty() || birthday.isEmpty() || address.isEmpty()) {

            showErrorMsg("请将信息填写完整");
        } else {

            editor.putString("nickName", nickName);
            editor.putString("introduce", introduce);
            editor.putString("sex", sex);
            editor.putString("birthday", birthday);
            editor.putString("address", address);
            editor.apply();
            postDataToServer();
        }
    }

    private void postDataToServer() {
        String userAccount = PrefUtils.getString(mBaseActivity, "phone", "");
        String absPath = Environment.getExternalStorageDirectory() + "/craftsman/" + PrefUtils.getString(mBaseActivity, "phone", "");
        File file = new File(absPath, IMAGE_FILE_NAME + ".JPEG");
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "headImage.JPEG", fileBody)
                .addFormDataPart("nickName", nickName)
                .addFormDataPart("introduce", introduce)
                .addFormDataPart("sex", sex)
                .addFormDataPart("birthday", birthday)
                .addFormDataPart("address", address)
                .addFormDataPart("userAccount", userAccount)
                .build();
        Request request = new Request.Builder()
                .url(Server.SERVER_MY_INFO)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "createHeadImage:fail" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "onResponse  info: " + responseJson);
                JSONObject jo = null;
                try {
                    jo = new JSONObject(responseJson);
                    String result = jo.getString("result");
                    if (result.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "保存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "个人资料编辑失败,请检查网络连接", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showInfo() {
        sp = getSharedPreferences(userClass, Context.MODE_PRIVATE);
        et_Nickname.setText(sp.getString("nickName", ""));
        et_Introduce.setText(sp.getString("introduce", ""));
        tvSex.setText(sp.getString("sex", ""));
        tvBirthday.setText(sp.getString("birthday", ""));
        tvAddress.setText(sp.getString("address", ""));
        ivMyImage.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/craftsman/" + PrefUtils.getString(mBaseActivity, "phone", "") + "/headImage.JPEG")));
    }

    private void showErrorMsg(String value) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mBaseActivity);
        dialog.setMessage(value);
        dialog.setPositiveButton("确定", null);
        dialog.show();
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
