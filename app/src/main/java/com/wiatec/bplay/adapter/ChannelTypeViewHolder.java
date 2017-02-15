package com.wiatec.bplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wiatec.bplay.R;

/**
 * Created by patrick on 2017/2/13.
 */

public class ChannelTypeViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivCountry;
    public TextView tvCountry;

    public ChannelTypeViewHolder(View itemView) {
        super(itemView);
        ivCountry = (ImageView) itemView.findViewById(R.id.iv_country);
        tvCountry = (TextView) itemView.findViewById(R.id.tv_country);
    }
}
