package com.joshua.experiment.http;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/5/6 14:06
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class ResponseInfo {
    private boolean alive;
    private boolean error;
    private String result;

    public ResponseInfo(boolean isAlive, boolean isError, String result) {
        this.alive = isAlive;
        this.error = isError;
        this.result = result;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        alive = alive;
    }

    public boolean isError() {
        return !error;
    }

    public void setError(boolean error) {
        error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
