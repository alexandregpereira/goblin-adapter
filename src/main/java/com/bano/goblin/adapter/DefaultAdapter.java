package com.bano.goblin.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by bk_alexandre.pereira on 05/12/2017.
 *
 */

public abstract class DefaultAdapter<T, E extends ViewDataBinding, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected final int mLayoutRes;
    protected final OnClickListener<T> mListener;
    private OnBottomItemListener mOnBottomItemListener;
    protected final Resources mResources;
    @NonNull
    protected final List<T> mItems;

    protected abstract void onBindViewHolder(E e, T t);

    public interface OnClickListener<T>{
        void onClicked(T t);
    }

    public interface OnBottomItemListener{
        void onBottom();
    }

    public DefaultAdapter(@NonNull List<T> items, int layoutRes, OnClickListener<T> listener){
        mLayoutRes = layoutRes;
        mItems = items;
        mListener = listener;
        mResources = null;
    }

    public DefaultAdapter(Context context, @NonNull List<T> items, int layoutRes, OnClickListener<T> listener){
        mLayoutRes = layoutRes;
        mItems = items;
        mListener = listener;
        mResources = context.getResources();
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        if(isPositionBottom(position) && mOnBottomItemListener != null) {
            mOnBottomItemListener.onBottom();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void replace(T t) {
        int i = mItems.indexOf(t);
        if(i >= 0){
            mItems.set(i, t);
            notifyItemChanged(i, t);
        }
    }

    public void remove(T t) {
        int i = mItems.indexOf(t);
        if(i < 0) return;
        mItems.remove(i);
        notifyItemRemoved(i);
    }

    public void setItems(@NonNull List<T> items) {
        for (T item : items) {
            setItem(item);
        }
    }

    public void setItem(T t) {
        int i = mItems.indexOf(t);
        if(i >= 0){
            mItems.set(i, t);
            notifyItemChanged(i, t);
        }
        else {
            this.mItems.add(t);
            notifyItemInserted(mItems.size() - 1);
        }
    }

    public void addItem(int position, T t) {
        this.mItems.add(position, t);
        notifyItemInserted(position);
    }

    public List<T> getItems(){
        return mItems;
    }

    public Resources getResources(){
        return mResources;
    }

    int getLayoutRes(){
        return mLayoutRes;
    }

    protected OnClickListener<T> getListener(){
        return mListener;
    }

    public void setOnBottomItemListener(OnBottomItemListener onBottomItemListener) {
        mOnBottomItemListener = onBottomItemListener;
    }

    private boolean isPositionBottom(int position) {
        return getItems().size() - 2 == position + 1;
    }
}
