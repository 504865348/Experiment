package com.joshua.experiment.pay.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.joshua.experiment.application.BaseApplication;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCookieJar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * class description here
 *
 * @author wqj
 * @version 1.0.0
 * @since 2017-03-29
 */
public class PayUtils {
    private String TAG = "PayUtils";
    private PaySuccess paySuccess;
    private OkHttpClient mClient;
    private Activity mActivity;

    public PayUtils(Activity activity) {
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(BaseApplication.getApplication()))
                .build();
        mActivity = activity;
    }

    public void setPaySuccess(PaySuccess paySuccess) {
        this.paySuccess = paySuccess;
    }


    /**
     * 视频支付
     */
    public void payV2(String type, String id, final float money) {
        /**
         * 访问服务器
         * 上传订单信息
         */

        RequestBody payParams = new FormBody.Builder()
                .add("method", Server.SERVER_SEND_ORDER)
                .add("id", id)
                .add("count", money + "")
                .add("type", type)
                .build();

        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(payParams)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseApplication.getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "responseJson: " + responseJson);
                try {
                    JSONObject jo = new JSONObject(responseJson);
                    final String orderInfo = jo.getString("result");
                    final String orderNumber = jo.getString("orderNumber");
                    Log.d(TAG, "result: " + orderInfo);
                    Log.d(TAG, "orderNumber: " + orderNumber);


                    //0元，则不需要付款
                    if (money == 0) {
                        Map<String, String> result = new HashMap<>();
                        result.put("resultStatus", 9000 + "");
                        postPayResult(result, orderNumber);
                        Log.d(TAG, "money:  free" );
                    } else {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(mActivity);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                postPayResult(result, orderNumber);
                            }
                        };


                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 将支付结果发送给服务器
     */
    private void postPayResult(final Map<String, String> result, final String orderNo) {
        if (result.get("resultStatus").equals("6001")) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseApplication.getApplication(), "操作已经取消", Toast.LENGTH_SHORT).show();
                }
            });
        }
        RequestBody resultParams = new FormBody.Builder()
                .add("method", Server.SERVER_SEND_ORDER_RESULT)
                .add("resultStatus", result.get("resultStatus"))
                .add("result", "null")
                .add("memo", "null")
//                .add("result", result.get("result"))
//                .add("memo", result.get("memo"))
                .add("orderNo", orderNo)
                .build();
        Log.d(TAG, "postPayResult: \n" + result.get("resultStatus") + "\n" + result.get("result") + "\n" + result.get("memo"));
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(resultParams)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                postPayResult(result, orderNo);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, "responseJson: " + responseJson);
                try {
                    JSONObject jo = new JSONObject(responseJson);
                    String result = jo.getString("result");
                    Log.d(TAG, "onResponse: " + result);
                    if (TextUtils.equals(result, "true")) {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        paySuccess.onSuccess(orderNo);
                                    }
                                });
                            }
                        });
                    } else {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "支付失败");
                                Toast.makeText(BaseApplication.getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
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
