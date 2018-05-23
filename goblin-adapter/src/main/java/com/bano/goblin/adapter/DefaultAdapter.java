package com.bano.goblin.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.os.Handler;
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
    private Handler mHandler;
    protected final Resources mResources;
    @NonNull
    protected final List<T> mItems;
    private int lastPageBottomPosition;
    private int pageSize;

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
        if(position > lastPageBottomPosition && isPagePositionBottom(position) && mOnBottomItemListener != null && mHandler != null) {
            lastPageBottomPosition = position;
            final OnBottomItemListener onBottomItemListener = mOnBottomItemListener;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onBottomItemListener.onBottom();
                }
            });
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
        setItemsInRange(0, getItems().size(), items);
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

    public void setItemsInRange(int startIndex, int toIndex, @NonNull List<T> items) {
        int index = startIndex;
        for (T item : items) {
            setItemAtIndex(index++, item);
        }

        List<T> allItems = getItems();

        int toIndexReal = startIndex + (allItems.size() - startIndex < toIndex ? allItems.size() - startIndex : toIndex);
        if(toIndexReal <= startIndex) return;
        List<T> itemsInRange = allItems.subList(startIndex, toIndexReal);
        if(items.size() < itemsInRange.size()) {
            for (T item : itemsInRange) {
                int i = items.indexOf(item);
                if(i < 0) {
                    remove(item);
                }
            }
        }
    }

    public void setItemAtIndex(int index, @NonNull T t) {
        int i = mItems.indexOf(t);
        if(i >= 0){
            if(i != index) {
                mItems.remove(i);
                notifyItemRemoved(i);
                int indexReal = index > mItems.size() ? mItems.size() : index;
                mItems.add(indexReal, t);
                notifyItemInserted(indexReal);
                return;
            }
            mItems.set(i, t);
            notifyItemChanged(i, t);
        }
        else {
            int indexReal = index > mItems.size() ? mItems.size() : index;
            this.mItems.add(indexReal, t);
            notifyItemInserted(indexReal);
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

    public void setOnBottomItemListener(int pageSize, Handler handler, OnBottomItemListener onBottomItemListener) {
        this.pageSize = pageSize;
        mHandler = handler;
        mOnBottomItemListener = onBottomItemListener;
    }

    public void clearLastPageBottomPosition() {
        lastPageBottomPosition = 0;
    }

    private boolean isPagePositionBottom(int position) {
        if(pageSize == 0) return false;
        int currentPosition = position + 1;
        return currentPosition % (pageSize - 2) == 0;
    }
}
