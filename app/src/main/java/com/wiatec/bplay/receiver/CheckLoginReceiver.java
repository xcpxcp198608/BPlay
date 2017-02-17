package com.wiatec.bplay.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.activity.LoginActivity;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 2017/2/17.
 */

public class CheckLoginReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //showLoginDialog();
    }

    private void showLoginDialog() {
        Logger.d("show dialog");
        AlertDialog alertDialog = new AlertDialog.Builder(Application.getContext()).create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        if(window == null){
            return;
        }
        window.setContentView(R.layout.dialog_login);
        Button btConfirm = (Button) window.findViewById(R.id.bt_confirm);
        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.getContext().startActivity(new Intent(Application.getContext() , LoginActivity.class));
            }
        });
    }
}
