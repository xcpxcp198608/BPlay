package com.wiatec.bplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiatec.bplay.R;

/**
 * Created by patrick on 2017/2/13.
 */

public class ChannelViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivChannelIcon;
    public TextView tvChannelName;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        ivChannelIcon = (ImageView) itemView.findViewById(R.id.iv_channel_icon);
        tvChannelName = (TextView) itemView.findViewById(R.id.tv_channel_name);
    }
}
