package com.joshua.experiment.entity.joshua;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/9/8 14:51
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class Order {

    /**
     * buyTime : 2017-09-06 21:33:50
     * goodId : 1
     * id : 19
     * orderId : 0906213350-1533
     * payStatus : true
     * price : 0.01
     * type : 1
     * userId : 18761996926
     */

    private String buyTime;
    private String goodId;
    private String id;
    private String orderId;
    private String payStatus;
    private String price;
    private String type;
    private String userId;

    public Order(String buyTime, String goodId, String id, String orderId, String payStatus, String price, String type, String userId) {
        this.buyTime = buyTime;
        this.goodId = goodId;
        this.id = id;
        this.orderId = orderId;
        this.payStatus = payStatus;
        this.price = price;
        this.type = type;
        this.userId = userId;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

