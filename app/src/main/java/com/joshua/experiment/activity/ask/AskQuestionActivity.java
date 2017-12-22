package com.joshua.experiment.activity.ask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.entity.joshua.OrderType;
import com.joshua.experiment.pay.util.PaySuccess;
import com.joshua.experiment.pay.util.PayUtils;
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

import static com.joshua.experiment.R.array.cost;


public class AskQuestionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_middle)
    TextView tv_middle;
    //@BindView(R.id.iv_right)
    // ImageView iv_right;
    @BindView(R.id.tv_answer)
    TextView tv_answer;
    @BindView(R.id.tv_cost)
    Spinner tv_cost;
    @BindView(R.id.et_question)
    EditText et_question;
    @BindView(R.id.iv_add_pic)
    ImageView iv_add_pic;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.btn_commit)
    Button btn_commit;

    private PopupWindow pop = null;
    private View parentView;
    private LinearLayout ll_popup;
    private String mAnswer, mCraftsAccount;
    private ProgressDialog mDialog;
    private File mFile;
    private String mCost = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        ButterKnife.bind(this);
        initToolBar();
        initView();
        initListener();
    }

    private void initListener() {
        //iv_right.setOnClickListener(this);
        iv_add_pic.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        tv_cost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private void initView() {
        mAnswer = getIntent().getStringExtra("answer");
        mCraftsAccount = getIntent().getStringExtra("craftsAccount");

        tv_middle.setText(mAnswer);
        tv_answer.setText("向" + mAnswer + "提问");
        //加载父布局
        parentView = getLayoutInflater().inflate(R.layout.activity_ask_question, null);
        InitPopWindow();
        mDialog = new ProgressDialog(this);
    }

    private void initToolBar() {
        //iv_right.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:
                //// TODO: 2017/6/1
                break;
            case R.id.iv_add_pic:
                pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_commit:
                //支付逻辑
                if (!mCost.equals("") || !mCost.isEmpty()) {
                    payQuestion();
                } else {
                    Toast.makeText(this, "请填写问题价格", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }

    private void payQuestion() {
        PayUtils utils = new PayUtils(mBaseActivity);
        utils.setPaySuccess(new PaySuccess() {
            @Override
            public void onSuccess(String orderNo) {
                postToServer(orderNo);
            }
        });
        //没有支付跳转支付
        utils.payV2(OrderType.TYPE_ASK_QUE, "null", Float.parseFloat(mCost));
    }

    private void postToServer(String orderNo) {
        String user = PrefUtils.getString(mBaseActivity, "phone", "");
        String question = et_question.getText().toString();
        if (question.trim().isEmpty()) {
            Toast.makeText(this, "请输入问题内容", Toast.LENGTH_SHORT).show();
            return;
        }

        String absPath = Environment.getExternalStorageDirectory() + "/bdsy/" + PrefUtils.getString(mBaseActivity, "phone", "");
        mFile = new File(absPath, IMAGE_FILE_NAME + ".JPEG");
        if (!mFile.exists()) {
            Toast.makeText(this, "请上传一张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("问题上传中，请稍等片刻");
        mDialog.show();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream;charset=utf-8"), mFile);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "question.JPEG", fileBody)
                .addFormDataPart("craftsman", mCraftsAccount)
                .addFormDataPart("questionWord", question)
                .addFormDataPart("money", mCost + "")
                .addFormDataPart("user", user)
                .addFormDataPart("orderNumber", orderNo)
                .build();
        Request request = new Request.Builder()
                .url(Server.SERVER_UPLOAD)
                .post(requestBody)
                .build();
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "run: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(mBaseActivity, "提问失败", Toast.LENGTH_SHORT).show();
                        mDialog.cancel();
                        if (mFile.exists()) {
                            mFile.delete();
                        }
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

                                Toast.makeText(mBaseActivity, "提问成功", Toast.LENGTH_SHORT).show();
                                if (mFile.exists()) {
                                    mFile.delete();
                                }
                            }
                        });
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mBaseActivity, "提问失败", Toast.LENGTH_SHORT).show();
                                if (mFile.exists()) {
                                    mFile.delete();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (mFile.exists()) {
                        mFile.delete();
                    }
                }

            }
        });
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
    private static final String IMAGE_FILE_NAME = "question";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    //保存为头像文件
                    MyUtils.saveBitmap(bitmap
                            , PrefUtils.getString(mBaseActivity, "phone", ""), IMAGE_FILE_NAME);
                    iv_add_pic.setImageBitmap(bitmap);
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
                    iv_add_pic.setImageBitmap(bitmap);
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


}
