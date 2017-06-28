package com.wiatec.bplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.wiatec.bplay.Application;
import com.wiatec.bplay.R;
import com.wiatec.bplay.beans.ChannelType;

import java.util.List;

/**
 * Created by patrick on 2017/2/13.
 */

public class ChannelTypeAdapter extends RecyclerView.Adapter<ChannelTypeViewHolder> {

    private List<ChannelType> list;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    public ChannelTypeAdapter( List<ChannelType> list) {
        this.list = list;
    }

    @Override
    public ChannelTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel_type_country , parent , false);
        ChannelTypeViewHolder channelTypeViewHolder = new ChannelTypeViewHolder(view);
        return channelTypeViewHolder;
    }

    @Override
    public void onBindViewHolder(final ChannelTypeViewHolder holder, final int position) {
        ChannelType channelType = list.get(position);
        Glide.with(Application.getContext()).load(channelType.getIcon()).placeholder(R.mipmap.bplay_logo)
                .error(R.mipmap.bplay_logo).into(holder.ivCountry);
        holder.tvCountry.setText(channelType.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(v, holder.getLayoutPosition());
                }
            }
        });
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemSelected(v, holder.getLayoutPosition() ,hasFocus);
                }
                if(hasFocus){
                    holder.tvCountry.setSelected(true);
                }else{
                    holder.tvCountry.setSelected(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener{
        void onItemClickListener(View view,int position);
    }

    public interface OnItemSelectedListener{
        void onItemSelected(View view , int position ,boolean hasFocus);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        mOnItemSelectedListener = onItemSelectedListener;
    }
}
