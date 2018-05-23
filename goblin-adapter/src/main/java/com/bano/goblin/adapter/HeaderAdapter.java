package com.bano.goblin.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk_alexandre.pereira on 05/12/2017.
 *
 */

public abstract class HeaderAdapter<K, V extends ViewDataBinding, T, E extends ViewDataBinding> extends DefaultAdapter<T, E, HeaderAdapter.ViewHolder<V, T, E>> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;

    private K mHeaderObj;
    private final int mHeaderLayoutRes;

    protected abstract void onBindHeader(V headerBinding, K headerObj);

    public HeaderAdapter(@NonNull Context context, @NonNull K headerObj, int headerLayoutRes, @NonNull List<T> items, int layoutRes, OnClickListener<T> listener) {
        super(context, new ArrayList<>(items), layoutRes, listener);
        mItems.add(0, null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
    }

    public HeaderAdapter(@NonNull K headerObj, int headerLayoutRes, @NonNull List<T> items, int layoutRes, OnClickListener<T> listener) {
        super(new ArrayList<>(items), layoutRes, listener);
        mItems.add(0, null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
    }

    @Override
    public HeaderAdapter.ViewHolder<V, T, E> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            E binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), parent, false);
            return new HeaderAdapter.ViewHolder<>(binding, getListener());
        }
        else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            V binding = DataBindingUtil.inflate(layoutInflater, mHeaderLayoutRes, parent, false);
            return new HeaderAdapter.ViewHolder<>(binding);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder<V, T, E> holder, int position) {
        super.onBindViewHolder(holder, position);
        if(getItemViewType(position) == TYPE_HEADER) {
            onBindHeader(holder.headerBinding, mHeaderObj);
        }
        else {
            T t = mItems.get(position);
            holder.binding.getRoot().setTag(t);
            this.onBindViewHolder(holder.binding, t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder<V, T, E> holder, int position, List<Object> payloads) {
        if(payloads != null && !payloads.isEmpty()){
            // update the specific view
            T t = (T) payloads.get(0);
            holder.binding.getRoot().setTag(t);
            this.onBindViewHolder(holder.binding, t);
        }else{
            // I have already overridden  the other onBindViewHolder(ViewHolder, int)
            // The method with 3 arguments is being called before the method with 2 args.
            // so calling super will call that method with 2 arguments.
            super.onBindViewHolder(holder,position,payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    @Override
    public List<T> getItems() {
        List<T> items = new ArrayList<>(super.getItems());
        if(items.size() > 0) {
            items.remove(0);
        }
        return items;
    }

    public void setHeader(K headerObj) {
        mHeaderObj = headerObj;
        notifyHeaderChanges();
    }
    
    public void notifyHeaderChanges() {
        notifyItemChanged(0);
    }

    @Override
    public void setItemAtIndex(int index, @NonNull T t) {
        super.setItemAtIndex(index + 1, t);
    }

    public K getHeader() {
        return mHeaderObj;
    }

    public static class ViewHolder<V extends ViewDataBinding, T, E extends ViewDataBinding> extends RecyclerView.ViewHolder{
        public final E binding;
        public final V headerBinding;

        public ViewHolder(E binding, final OnClickListener<T> listener){
            super(binding.getRoot());
            this.binding = binding;
            headerBinding = null;
            if(listener != null) {
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onClick(View view) {
                        T t = (T) view.getTag();
                        listener.onClicked(t);
                    }
                });
            }
        }

        public ViewHolder(V binding){
            super(binding.getRoot());
            this.binding = null;
            this.headerBinding = binding;
        }
    }
}
