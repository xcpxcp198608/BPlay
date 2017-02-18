package com.wiatec.bplay.rx_event;

/**
 * Created by patrick on 2017/2/18.
 */

public class RepeatLogin {
    private int code;

    public RepeatLogin() {
    }

    public RepeatLogin(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "RepeatLogin{" +
                "code=" + code +
                '}';
    }
}
