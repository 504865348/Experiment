package com.joshua.experiment.pay.util;

/**
 * 支付成功的回调
 *
 * Created by wangqiang on 2017/4/16.
 */

public interface PaySuccess {
    void onSuccess(String orderNo);
}
