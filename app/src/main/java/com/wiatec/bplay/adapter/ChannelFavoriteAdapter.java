package com.wiatec.bplay.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelInfo;

import java.util.List;

/**
 * Created by patrick on 2017/2/16.
 */

public class ChannelFavoriteAdapter extends RecyclerView.Adapter<ChannelFavoriteViewHolder> {

    private List<ChannelInfo> list;
    private View view;
    private OnItemClickListener mOnItemClickListener;

    public ChannelFavoriteAdapter(List<ChannelInfo> list) {
        this.list = list;
    }

    @Override
    public ChannelFavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel_favorite , parent , false);
        ChannelFavoriteViewHolder viewHolder = new ChannelFavoriteViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChannelFavoriteViewHolder holder, final int position) {
        ChannelInfo channelInfo = list.get(position);
        Glide.with(Application.getContext())
                .load(channelInfo.getIcon())
                .placeholder(R.mipmap.bplay_logo)
                .error(R.mipmap.bplay_logo)
                .dontAnimate()
                .into(holder.ivChannelIcon);
        holder.tvChannelName.setText(channelInfo.getName());
        if(mOnItemClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
