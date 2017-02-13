package com.wiatec.bplay.presenter;

import com.wiatec.bplay.activity.IRegisterActivity;
import com.wiatec.bplay.beans.Result;
import com.wiatec.bplay.data.ILoginData;
import com.wiatec.bplay.data.LoginData;
import com.wiatec.bplay.utils.Logger;

/**
 * Created by patrick on 2017/2/9.
 */

public class RegisterPresenter extends BasePresenter<IRegisterActivity> {
    private IRegisterActivity iRegisterActivity;
    private ILoginData iLoginData;

    public RegisterPresenter(IRegisterActivity iRegisterActivity) {
        this.iRegisterActivity = iRegisterActivity;
        iLoginData = new LoginData();
    }

    public void register(String userName ,String password ,String email){
        if(iLoginData != null){
            iLoginData.register(userName, password, email, new ILoginData.OnLoadListener() {
                @Override
                public void onSuccess(boolean isSuccess , Result result) {
                    iRegisterActivity.register(isSuccess ,result);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }

}
