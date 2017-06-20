package com.bano.goblin.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 *
 * Provides a accordion implementation
 */

public abstract class BaseAccordionAdapter<T, E extends ViewDataBinding> extends BaseAdapter<BaseAccordionAdapter.AccordionContainer<T>, E>{

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_CATEGORY = 2;

    private final boolean mExpandOnlyOneFlag;
    private AccordionContainer<T> mPreviousAccordionOpened;

    public abstract void onCategoryBindViewHolder(E e, String category);
    public abstract void onItemBindViewHolder(E e, T t);

    public BaseAccordionAdapter(ArrayList<AccordionContainer<T>> items,
                                @LayoutRes int categoryLayoutRes, boolean expandOnlyOne,
                                OnClickListener<AccordionContainer<T>> listener){
        super(items, categoryLayoutRes, listener);
        mExpandOnlyOneFlag = expandOnlyOne;
    }

    @Override
    public BaseAdapter.ViewHolder<AccordionContainer<T>, E> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        E binding = DataBindingUtil.inflate(layoutInflater, getLayoutRes(), parent, false);
        return new BaseAdapter.ViewHolder<>(binding, new OnClickListener<AccordionContainer<T>>() {
            @Override
            public void onClicked(AccordionContainer<T> accordionContainer) {
                if(accordionContainer.type == TYPE_CATEGORY) {
                    if(accordionContainer.isOpen()){
                        collapse(accordionContainer);
                    }
                    else {
                        if(mExpandOnlyOneFlag && mPreviousAccordionOpened != null){
                            collapse(mPreviousAccordionOpened);
                        }
                        expand(accordionContainer);
                    }
                }
                else getListener().onClicked(accordionContainer);
            }
        });
    }

    private void expand(AccordionContainer<T> accordionContainer){
        mPreviousAccordionOpened = accordionContainer;
        accordionContainer.setOpen(true);
        int position = accordionContainer.position + 1;
        for (T t : accordionContainer.tList) {
            addItem(position, new AccordionContainer<>(t, TYPE_ITEM));
            ++position;
        }

        for(int i = position; i < getItems().size(); i++){
            getItems().get(i).setPosition(i);
        }
    }

    private void collapse(AccordionContainer<T> accordionContainer){
        accordionContainer.setOpen(false);
        for (T t : accordionContainer.tList) {
            remove(new AccordionContainer<>(t, TYPE_ITEM));
        }

        for(int i = accordionContainer.position + 1; i < getItems().size(); i++){
            getItems().get(i).setPosition(i);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder<AccordionContainer<T>, E> holder, int position) {
        AccordionContainer<T> accordionContainer = getItems().get(position);
        accordionContainer.setPosition(position);
        holder.binding.getRoot().setTag(accordionContainer);
        switch (accordionContainer.type){
            case TYPE_CATEGORY:
                this.onCategoryBindViewHolder(holder.binding, accordionContainer.category);
                break;
            case TYPE_ITEM:
                this.onItemBindViewHolder(holder.binding, accordionContainer.t);
                break;
        }
    }

    @Override
    protected void onBindViewHolder(E e, AccordionContainer<T> accordionContainer) {

    }

    public static class AccordionContainer<T>{
        @IntDef(value = {TYPE_ITEM, TYPE_CATEGORY})
        @Retention(RetentionPolicy.SOURCE)
        private @interface TypeFlags {}

        private final String category;
        public final T t;
        private final ArrayList<T> tList;
        public final int type;
        private int position;
        private boolean open;

        public AccordionContainer(String category, ArrayList<T> tList) {
            this.tList = tList;
            this.type = TYPE_CATEGORY;
            this.category = category;
            this.t = null;
        }

        private AccordionContainer(T t, @TypeFlags int type) {
            this.category = null;
            this.t = t;
            this.tList = null;
            this.type = type;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        private boolean isOpen() {
            return open;
        }

        private void setOpen(boolean open) {
            this.open = open;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AccordionContainer<?> that = (AccordionContainer<?>) o;

            return t != null ? t.equals(that.t) : that.t == null;
        }

        @Override
        public int hashCode() {
            return t != null ? t.hashCode() : 0;
        }
    }
}
