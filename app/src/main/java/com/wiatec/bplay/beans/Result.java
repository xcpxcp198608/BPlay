package com.wiatec.bplay.beans;

/**
 * Created by patrick on 2017/2/9.
 */

public class Result {
    private int code;
    private String status;
    private String info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
