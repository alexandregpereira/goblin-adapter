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

public abstract class HeaderPaginationAdapter<K, V extends ViewDataBinding, T, E extends ViewDataBinding, L extends ViewDataBinding> extends DefaultAdapter<T, E, HeaderPaginationAdapter.ViewHolder<V, T, E, L>> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_BOTTOM = 3;

    private K mHeaderObj;
    private final int mHeaderLayoutRes;
    private final int mBottomLayoutRes;
    private boolean mLoading;

    protected abstract void onBindHeader(V headerBinding, K headerObj);
    protected abstract void onBindBottom(L headerBinding, boolean isLoading);

    public HeaderPaginationAdapter(@NonNull Context context,
                                   @NonNull K headerObj,
                                   int headerLayoutRes,
                                   @NonNull List<T> items,
                                   int layoutRes,
                                   int bottomLayout,
                                   OnClickListener<T> listener) {
        super(context, new ArrayList<>(items), layoutRes, listener);
        mItems.add(0, null);
        mItems.add(null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
        mBottomLayoutRes = bottomLayout;
    }

    public HeaderPaginationAdapter(@NonNull K headerObj,
                                   int headerLayoutRes,
                                   @NonNull List<T> items,
                                   int layoutRes,
                                   int bottomLayout,
                                   OnClickListener<T> listener) {
        super(new ArrayList<>(items), layoutRes, listener);
        mItems.add(0, null);
        mItems.add(null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
        mBottomLayoutRes = bottomLayout;
    }

    @Override
    public HeaderPaginationAdapter.ViewHolder<V, T, E, L> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            E binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), parent, false);
            return new HeaderPaginationAdapter.ViewHolder<>(binding, getListener());
        }
        else if(viewType == TYPE_HEADER) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            V binding = DataBindingUtil.inflate(layoutInflater, mHeaderLayoutRes, parent, false);
            return new HeaderPaginationAdapter.ViewHolder<>(binding.getRoot(), binding, null);
        }
        else {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            L binding = DataBindingUtil.inflate(layoutInflater, mBottomLayoutRes, parent, false);
            return new HeaderPaginationAdapter.ViewHolder<>(binding.getRoot(), null, binding);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder<V, T, E, L> holder, int position) {
        super.onBindViewHolder(holder, position);
        if(getItemViewType(position) == TYPE_ITEM) {
            T t = mItems.get(position);
            holder.binding.getRoot().setTag(t);
            this.onBindViewHolder(holder.binding, t);
        }
        else if(getItemViewType(position) == TYPE_HEADER) {
            onBindHeader(holder.headerBinding, mHeaderObj);
        }
        else if(getItemViewType(position) == TYPE_BOTTOM){
            onBindBottom(holder.bottomBinding, mLoading);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(ViewHolder<V, T, E, L> holder, int position, List<Object> payloads) {
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
        else if(position == mItems.size() - 1)
            return TYPE_BOTTOM;

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
        notifyItemChanged(0);
    }

    public void setLoading(boolean loading) {
        mLoading = loading;
        notifyItemChanged(getItemCount());
    }

    public static class ViewHolder<V extends ViewDataBinding, T, E extends ViewDataBinding, L extends ViewDataBinding> extends RecyclerView.ViewHolder{
        public final E binding;
        public final V headerBinding;
        public final L bottomBinding;

        public ViewHolder(E binding, final OnClickListener<T> listener){
            super(binding.getRoot());
            this.binding = binding;
            headerBinding = null;
            bottomBinding = null;
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

        public ViewHolder(View root, V binding, L bottomBinding){
            super(root);
            this.binding = null;
            this.headerBinding = binding;
            this.bottomBinding = bottomBinding;
        }
    }
}
