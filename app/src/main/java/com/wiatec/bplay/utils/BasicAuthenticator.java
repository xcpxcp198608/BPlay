package com.wiatec.bplay.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by patrick on 2017/2/24.
 */

public class BasicAuthenticator extends Authenticator {

    private String userName;
    private String password;

    public BasicAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName , password.toCharArray());
    }
}
