package com.wiatec.bplay.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by patrick on 2017/2/14.
 */

public class RecyclerViewLinearDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint = null;
    private Drawable mDrawable = null;
    private int mDecorationHeight = 2;
    private int mOrientation ;
    private static final int [] ATTRS = new int [] {android.R.attr.listDivider} ;

    /**
     * 默认分割线
     * @param context 上下文
     * @param orientation 方向
     */
    public RecyclerViewLinearDecoration (Context context , int orientation){
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
          throw new IllegalArgumentException("orientation params error");
        }
        mOrientation = orientation;
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDrawable = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 按传入的资源图片为分割线
     * @param context 上下文
     * @param orientation 方向
     * @param drawableResId 图片资源ID
     */
    public RecyclerViewLinearDecoration (Context context , int orientation ,int drawableResId){
        this(context ,orientation);
        mDrawable = ContextCompat.getDrawable(context , drawableResId);
        mDecorationHeight = mDrawable.getIntrinsicHeight();
    }

    /**
     * 自定义分割线高度，颜色
     * @param context 上下文
     * @param orientation 方向
     * @param decorationHeight 分割线高度
     * @param decorationColor 分割线颜色
     */
    public RecyclerViewLinearDecoration (Context context , int orientation ,int decorationHeight ,int decorationColor){
        this(context ,orientation);
        mDecorationHeight = decorationHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(decorationColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,mDecorationHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(mOrientation == LinearLayoutManager.VERTICAL){
            drawVertical(c , parent);
        }else{
            drawHorizontal(c , parent);
        }
    }

    //绘制纵向分割线
    private void drawVertical(Canvas c , RecyclerView parent){
        final int top = parent.getPaddingTop();
        final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for(int i = 0 ; i <childCount ; i ++){
            final View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            final int left = view.getRight() + layoutParams.rightMargin;
            final int right = left + mDecorationHeight;
            if(mDrawable != null){
                mDrawable.setBounds(left,top,right,bottom);
                mDrawable.draw(c);
            }
            if(mPaint != null){
                c.drawRect(left,top,right,bottom , mPaint);
            }
        }
    }

    //绘制横向分割线
    private void drawHorizontal(Canvas c , RecyclerView parent){
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childCount =  parent.getChildCount();
        for (int i = 0 ; i < childCount ; i ++){
            final View view = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            final int top = view.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDecorationHeight;
            if(mDrawable != null){
                mDrawable.setBounds(left,top,right,bottom);
                mDrawable.draw(c);
            }
            if(mPaint != null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

}
