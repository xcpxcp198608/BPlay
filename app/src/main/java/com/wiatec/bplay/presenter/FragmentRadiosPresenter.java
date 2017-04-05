package com.wiatec.bplay.presenter;

import com.wiatec.bplay.beans.ChannelInfo;
import com.wiatec.bplay.data.IChannelData;
import com.wiatec.bplay.fragment.IFragmentRadios;
import com.wiatec.bplay.utils.Logger;

import java.util.List;

/**
 * Created by patrick on 2017/2/10.
 */

public class FragmentRadiosPresenter extends BasePresenter<IFragmentRadios> {
    private IFragmentRadios iFragmentRadios ;


    public FragmentRadiosPresenter(IFragmentRadios iFragmentRadios) {
        this.iFragmentRadios = iFragmentRadios;
    }

    public void loadChannelRadios(String style){
        if(iChannelData!=null){
            iChannelData.showByStyle(style, new IChannelData.OnLoadListener() {
                @Override
                public void onSuccess(List<ChannelInfo> list, boolean finished) {
                    iFragmentRadios.loadChannelRadios(list);
                }

                @Override
                public void onFailure(String e) {
                    Logger.d(e);
                }
            });
        }
    }
}
