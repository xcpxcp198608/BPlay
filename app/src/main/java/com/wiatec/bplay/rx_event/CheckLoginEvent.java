package com.wiatec.bplay.rx_event;

/**
 * Created by patrick on 2017/2/18.
 */

public class CheckLoginEvent {
    public static final int CODE_LOGIN_NORMAL = 0;
    public static final int CODE_LOGIN_REPEAT = 1;

    private int code;

    public CheckLoginEvent(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CheckLoginEvent{" +
                "code=" + code +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
