package com.example.ck.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Created by ck on 2017/10/15.
 */

public class MyLayoutManage extends LinearLayoutManager {
    SparseArray<ViewBean> views = new SparseArray<>(10);
    private View viewForPosition;
    private View viewForPosition1;
    private int itemCount;

    public MyLayoutManage(Context context) {
        super(context);
    }

    public MyLayoutManage(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLayoutManage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            Field mItemCount = state.getClass().getDeclaredField("mItemCount");
            mItemCount.setAccessible(true);
            if (itemCount == 0)
                itemCount = state.getItemCount();
            mItemCount.setInt(state, itemCount - 1);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        super.onLayoutChildren(recycler, state);


        if (viewForPosition != null)

            removeView(viewForPosition);
        try {
            Field mItemCount = state.getClass().getDeclaredField("mItemCount");
            mItemCount.setAccessible(true);
            mItemCount.setInt(state, itemCount);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        viewForPosition = recycler.getViewForPosition(state.getItemCount() - 1);
        addView(viewForPosition);
        viewForPosition.layout(100, 100, 300, 200);

        if (viewForPosition1 != null)
            removeView(viewForPosition1);
        int firstCompletelyVisibleItemPosition = findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition > 10) {
            viewForPosition1 = recycler.getViewForPosition(10);
            addView(viewForPosition1);
            viewForPosition1.layout(100, 0, 200, 100);
        }

    }


    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == itemCount) {
            try {
                Field mItemCount = state.getClass().getDeclaredField("mItemCount");
                mItemCount.setAccessible(true);
                if (itemCount == 0)
                    itemCount = state.getItemCount();
                mItemCount.setInt(state, itemCount - 1);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        if (viewForPosition1 != null)
            removeView(viewForPosition1);
        if (viewForPosition != null)
            removeView(viewForPosition);


        int i = super.scrollVerticallyBy(dy, recycler, state);


        try {
            Field mItemCount = state.getClass().getDeclaredField("mItemCount");
            mItemCount.setAccessible(true);
            mItemCount.setInt(state, itemCount);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


/**
 * 静态浮动 item在最后
 */
        viewForPosition = recycler.getViewForPosition(itemCount - 1);
        addView(viewForPosition);
        viewForPosition.layout(100, 100, 300, 200);


        /**
         * 浮动
         */
        int firstCompletelyVisibleItemPosition = findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition > 10) {
            viewForPosition1 = recycler.getViewForPosition(10);
            addView(viewForPosition1);
            viewForPosition1.layout(100, 0, 200, 100);
        }

        return i;
    }

    public void addFlowView(ViewBean v) {
        views.put(v.id, v);
    }

    public static class ViewBean {
        int id;
        View view;
        int x, y;

        public ViewBean(View view, int x, int y, int id) {
            this.view = view;
            this.x = x;
            this.y = y;
            this.id = id;
        }
    }
}
