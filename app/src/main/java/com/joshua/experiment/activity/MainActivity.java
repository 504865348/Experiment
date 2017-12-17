package com.joshua.experiment.activity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.account.LoginActivity;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.activity.error.DataErrorActivity;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.fragment.BaseFragment;
import com.joshua.experiment.fragment.BillBoardFragment;
import com.joshua.experiment.fragment.CraftsInfoFragment;
import com.joshua.experiment.fragment.FindFragment;
import com.joshua.experiment.fragment.HomeFragment;
import com.joshua.experiment.fragment.PublicInfoFragment;
import com.joshua.experiment.fragment.QAFragment;
import com.joshua.experiment.http.HttpCookieJar;
import com.joshua.experiment.http.ResponseInfo;
import com.joshua.experiment.utils.PrefUtils;

import java.io.IOException;
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

public class MainActivity extends BaseActivity {

    private int position = 0;
    private List<BaseFragment> mFragments;
    private BaseFragment tempFragment = null;
    private IntentFilter mFilter;
    private String intentAction;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    private int netType;

    @BindView(R.id.main_frame_content)
    FrameLayout mMainFragment;
    @BindView(R.id.main_rg_guide)
    RadioGroup mMainRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        checkCookie();
        permissionRequest();
    }


    private void checkCookie() {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(this))
                .build();
        RequestBody params = new FormBody.Builder()
                .add("method", Server.BILLBOARD_HOT)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "onResponse: " + responseJson);
                Gson gson = new Gson();
                ResponseInfo responseInfo = gson.fromJson(responseJson, ResponseInfo.class);
                if (!responseInfo.isAlive()) {
                    Log.d(TAG, "onResponse: cookie过期");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                else if (responseInfo.isError()) {
                    Log.d(TAG, "onResponse: 出现异常");
                    startActivity(new Intent(MainActivity.this, DataErrorActivity.class));
                } else {
                    String result = responseInfo.getResult();
                    Log.d(TAG, "onResponseAgain: " + result);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initData();
                        }
                    });

                }
            }
        });
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new BillBoardFragment());
        mFragments.add(new QAFragment());
        mFragments.add(new FindFragment());
        String type = PrefUtils.getString(mBaseActivity, "type", "normal");
        if (type.equals("normal")) {
            mFragments.add(new PublicInfoFragment());
        } else {
            mFragments.add(new CraftsInfoFragment());
        }
        setRadioGroupListener();
        //mFilter = new IntentFilter();
        // mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //registerReceiver(myNetReceiver, mFilter);
    }

    /**
     * 监听网络连接是否正常
     */
   /* private BroadcastReceiver myNetReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            intentAction = intent.getAction();
            if (intentAction.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    netType = netInfo.getType();
                    if (netType == ConnectivityManager.TYPE_WIFI) {

                    } else if (netType == ConnectivityManager.TYPE_ETHERNET) {

                    } else if (netType == ConnectivityManager.TYPE_MOBILE) {

                    }
                } else {
                    Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("请检查网络连接");
                    dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                        }
                    });
                    dialog.show();
                }
            }
        }
    };*/

    /**
     * 导航栏的点击监听事件
     */
    private void setRadioGroupListener() {
        mMainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_rb_home_page:
                        position = 0;
                        break;
                    case R.id.main_rb_billboard:
                        position = 1;
                        break;
                    case R.id.main_rb_question:
                        position = 2;
                        break;
                    case R.id.main_rb_discover:
                        position = 3;
                        break;
                    case R.id.main_rb_my_info:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment fragment = getFragment(position);
                switchFragment(fragment);
            }
        });
        mMainRadioGroup.check(R.id.main_rb_home_page);
    }

    /**
     * 根据位置得到对应 Fragment
     */
    private BaseFragment getFragment(int position) {
        if (mFragments != null && mFragments.size() > 0) {
            return mFragments.get(position);
        }
        return null;
    }

    /**
     * 切换 Fragment 的方法
     */
    private void switchFragment(BaseFragment nextFragment) {

        if (tempFragment != nextFragment) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            /**
             * 如果该 Fragment 未被添加，则将其添加
             * 如果已经添加，则直接显示
             */
            if (!nextFragment.isAdded()) {
                if (tempFragment != null) {
                    transaction.hide(tempFragment);
                }
                transaction.add(R.id.main_frame_content, nextFragment).commit();
            } else {
                if (tempFragment != null) {
                    transaction.hide(tempFragment);
                }
                transaction.show(nextFragment).commit();
            }
            /**
             * 将当前要显示的 Fragment 保存为缓存
             */
            tempFragment = nextFragment;
        }
    }


    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    public void permissionRequest() {
        if (Build.VERSION.SDK_INT >= 23) {
            //读取SD卡
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
            //读取摄像头
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
            }
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权开启失败去，请到设置中手动授权", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权开启失败去，请到设置中手动授权", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权开启失败去，请到设置中手动授权", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private long mPressedTime = 0;

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(mBaseActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            PrefUtils.setBoolean(mBaseActivity, "isLogin", false);
            Intent intent = new Intent("com.joshua.exit");
            sendBroadcast(intent);
        }
    }
}
