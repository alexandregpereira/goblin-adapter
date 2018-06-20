package com.bano.goblin.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bk_alexandre.pereira on 05/12/2017.
 *
 * @param <K> Header Obj
 * @param <V> ViewDataBinding header Obj
 * @param <T> Item Obj
 * @param <E> ViewDataBinding Item Obj
 * @param <N> ViewDataBinding bottom Obj
 */
@SuppressWarnings("unchecked")
public abstract class HeaderBottomAdapter<K, V extends ViewDataBinding, T, E extends ViewDataBinding, N extends ViewDataBinding> extends DefaultAdapter<T, E, HeaderBottomAdapter.ViewHolder<T>> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_BOTTOM = 3;

    private K mHeaderObj;
    private final int mHeaderLayoutRes;
    private final int mBottomLayoutRes;
    private boolean showBottomLayout;
    private boolean tryAgain;
    protected TryAgainListener tryAgainListener;

    protected abstract void onBindHeader(V headerBinding, K headerObj);
    protected abstract void onBindBottom(N bottomBinding);

    public HeaderBottomAdapter(@NonNull Context context,
                               @NonNull K headerObj,
                               @IntegerRes int headerLayoutRes,
                               @IntegerRes int bottomLayoutRes,
                               @NonNull List<T> items,
                               @IntegerRes int itemLayoutRes,
                               @Nullable OnClickListener<T> listener) {
        super(context, new ArrayList<>(items), itemLayoutRes, listener);
        mItems.add(0, null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
        mBottomLayoutRes = bottomLayoutRes;
    }

    public HeaderBottomAdapter(@NonNull K headerObj,
                               @LayoutRes int headerLayoutRes,
                               @LayoutRes int bottomLayoutRes,
                               @NonNull List<T> items,
                               @LayoutRes int itemLayoutRes,
                               @Nullable OnClickListener<T> listener) {
        super(new ArrayList<>(items), itemLayoutRes, listener);
        mItems.add(0, null);
        mHeaderObj = headerObj;
        mHeaderLayoutRes = headerLayoutRes;
        mBottomLayoutRes = bottomLayoutRes;
    }

    public interface TryAgainListener {
        void onClick();
    }

    @NonNull
    @Override
    public HeaderBottomAdapter.ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_ITEM) {
            E binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), parent, false);
            return new HeaderBottomAdapter.ViewHolder<>(binding, getListener());
        }
        else if(viewType == TYPE_BOTTOM) {
            N binding = DataBindingUtil.inflate(layoutInflater, mBottomLayoutRes, parent, false);
            return new HeaderBottomAdapter.ViewHolder<>(binding);
        }
        else {
            V binding = DataBindingUtil.inflate(layoutInflater, mHeaderLayoutRes, parent, false);
            return new HeaderBottomAdapter.ViewHolder<>(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position) {
        super.onBindViewHolder(holder, position);
        int viewType = getItemViewType(position);
        if(viewType == TYPE_HEADER) {
            onBindHeader((V) holder.binding, mHeaderObj);
        }
        else if(viewType == TYPE_BOTTOM) {
            onBindBottom((N) holder.binding);
        }
        else {
            T t = mItems.get(position);
            holder.binding.getRoot().setTag(t);
            this.onBindViewHolder((E) holder.binding, t);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
        if(!payloads.isEmpty()){
            // update the specific view
            try {
                T t = (T) payloads.get(0);
                holder.binding.getRoot().setTag(t);
                this.onBindViewHolder((E)holder.binding, t);
            } catch (ClassCastException ex) {
                ex.printStackTrace();
            }
        }else{
            // I have already overridden  the other onBindViewHolder(ViewHolder, int)
            // The method with 3 arguments is being called before the method with 2 args.
            // so calling super will call that method with 2 arguments.
            super.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(showBottomLayout && getItemCount() == position + 1)
            return TYPE_BOTTOM;

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

    public void notifyBottomChanges() {
        int position = getItemCount() - 1;
        if(position < 0) return;
        notifyItemChanged(position);
    }

    public boolean isShowBottomLayout() {
        return showBottomLayout;
    }

    public void setShowBottomLayout(boolean showBottomLayout) {
        this.showBottomLayout = showBottomLayout;
        int position = getItemCount() - 1;
        if(position < 0) return;
        if(showBottomLayout) notifyItemInserted(position + 1);
        else notifyItemRemoved(position);
    }

    public boolean isTryAgain() {
        return tryAgain;
    }

    public void setTryAgain(boolean tryAgain) {
        this.tryAgain = tryAgain;
        notifyBottomChanges();
    }

    @Nullable
    public TryAgainListener getTryAgainListener() {
        return tryAgainListener;
    }

    public void setTryAgainListener(TryAgainListener tryAgainListener) {
        this.tryAgainListener = tryAgainListener;
    }

    @Override
    public void setItemAtIndex(int index, @NonNull T t) {
        super.setItemAtIndex(index + 1, t);
    }

    public K getHeader() {
        return mHeaderObj;
    }

    public static class ViewHolder<T> extends RecyclerView.ViewHolder{
        public final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding, final OnClickListener<T> listener){
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

        public ViewHolder(ViewDataBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
