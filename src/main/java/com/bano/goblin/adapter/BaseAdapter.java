package com.bano.goblin.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 *
 * A base Recycler View adapter that uses DataBinding
 */

public abstract class BaseAdapter<T, E extends ViewDataBinding> extends DefaultAdapter<T, E, BaseAdapter.ViewHolder<T, E>>{

    public BaseAdapter(@NonNull List<T> items, int layoutRes, OnClickListener<T> listener){
        super(items, layoutRes, listener);
    }

    public BaseAdapter(Context context, @NonNull List<T> items, int layoutRes, OnClickListener<T> listener){
        super(context, items, layoutRes, listener);
    }

    @Override
    public ViewHolder<T, E> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        E binding = DataBindingUtil.inflate(layoutInflater, mLayoutRes, parent, false);
        return new ViewHolder<>(binding, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder<T, E> holder, int position) {
        T t = mItems.get(position);
        holder.binding.getRoot().setTag(t);
        this.onBindViewHolder(holder.binding, t);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder<T, E> holder, int position, List<Object> payloads) {
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

    public static class ViewHolder<T, E extends ViewDataBinding> extends RecyclerView.ViewHolder{
        public final E binding;

        public ViewHolder(E binding, final OnClickListener<T> listener){
            super(binding.getRoot());
            this.binding = binding;
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

        ViewHolder(View view){
            super(view);
            this.binding = null;
        }
    }
}
