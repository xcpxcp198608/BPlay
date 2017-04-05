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
 * Created by patrick on 2017/2/13.
 */

public class ChannelAdapter extends RecyclerView.Adapter<ChannelViewHolder> {

    private List<ChannelInfo> list;
    private View view= null;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ChannelAdapter(List<ChannelInfo> list) {
        this.list = list;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel ,parent ,false);
        ChannelViewHolder channelViewHolder = new ChannelViewHolder(view);
        return channelViewHolder;
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, final int position) {
        ChannelInfo channelInfo = list.get(position);
        Glide.with(Application.getContext())
                .load(channelInfo.getIcon())
                .placeholder(R.mipmap.bplay_logo)
                .error(R.mipmap.bplay_logo)
                .dontAnimate()
                .dontTransform()
                .into(holder.ivChannelIcon);
        holder.tvChannelName.setText(channelInfo.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(mOnItemSelectedListener != null){
                        mOnItemSelectedListener.onItemSelected(v,position);
                    }
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(mOnItemLongClickListener != null){
                    mOnItemLongClickListener.onItemLongClick(v,position);
                }
                return false;
            }
        });
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

    public interface OnItemSelectedListener{
        void onItemSelected(View view , int position);
    }
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view , int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        mOnItemLongClickListener = onItemLongClickListener;
    }
}
