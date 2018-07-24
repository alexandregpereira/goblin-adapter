package com.bano.goblin.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by Alexandre Pereira on 10/10/2017.
 *
 * Provides an recycler view adapter with section expanded
 */
abstract public class BaseSectionAdapter<T> extends BaseAdapter<SectionContainer<T>, ViewDataBinding> {

    @IntDef(value = {EXPAND_ONLY_ONE, EXPAND_MANY, NOT_EXPANDABLE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ExpandableFlags {}

    public static final int EXPAND_ONLY_ONE = 1;
    public static final int EXPAND_MANY = 2;
    public static final int NOT_EXPANDABLE = 3;

    private SectionContainer<T> mPreviousAccordionOpened;
    @ExpandableFlags
    private final int mExpandFlag;

    abstract protected ViewDataBinding getViewDataBinding(int viewType, @NonNull LayoutInflater layoutInflater, @NonNull ViewGroup parent);
    abstract protected int getViewType(@NonNull Object item);
    abstract protected void bindData(@NonNull SectionContainer<T> sectionContainer, @Nullable Object item, @NonNull ViewDataBinding viewDataBinding);

    public BaseSectionAdapter(List<SectionContainer<T>> items, @ExpandableFlags int expand, OnClickListener<SectionContainer<T>> listener) {
        super(items, 0, listener);
        mExpandFlag = expand;
    }

    @Override
    protected void onBindViewHolder(ViewDataBinding viewDataBinding, SectionContainer<T> sectionContainer) {
        if(sectionContainer == null || viewDataBinding == null) return;
        Object item = sectionContainer.item;
        bindData(sectionContainer, item, viewDataBinding);
    }

    @Override
    public ViewHolder<SectionContainer<T>, ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent == null) throw new IllegalArgumentException("parent is null");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        OnClickListener<SectionContainer<T>> accordionListener = new OnClickListener<SectionContainer<T>>() {
            @Override
            public void onClicked(SectionContainer<T> sectionContainer) {
                if(sectionContainer == null) return;
                if (isExpandable() && sectionContainer.type == SectionContainer.TYPE_CATEGORY) {
                    if (sectionContainer.isOpen()) {
                        collapse(sectionContainer);
                    }
                    else {
                        final SectionContainer<T> previousAccordionOpened = mPreviousAccordionOpened;
                        if (mExpandFlag == EXPAND_ONLY_ONE && previousAccordionOpened != null) {
                            collapse(previousAccordionOpened);
                        }
                        expand(sectionContainer);
                    }
                }
                else if(getListener() != null){
                    getListener().onClicked(sectionContainer);
                }
            }
        };

        ViewDataBinding binding = getViewDataBinding(viewType, layoutInflater, parent);
        return new ViewHolder<>(binding, accordionListener);
    }

    private void expand(SectionContainer<T> sectionContainer) {
        mPreviousAccordionOpened = sectionContainer;
        sectionContainer.setOpen(true);
        replace(sectionContainer);
        int position = sectionContainer.getPosition() + 1;
        for (Object item : sectionContainer.itemList) {
            addItem(position, new SectionContainer<T>(item));
            ++position;
        }

        for(int i = position; i < getItems().size(); i++){
            getItems().get(i).setPosition(i);
        }
    }

    private void collapse(SectionContainer<T> sectionContainer){
        sectionContainer.setOpen(false);
        replace(sectionContainer);
        for (Object item : sectionContainer.itemList) {
            remove(new SectionContainer<T>(item));
        }

        for(int i = sectionContainer.getPosition() + 1; i < getItems().size(); i++){
            getItems().get(i).setPosition(i);
        }
    }

    @Override
    public int getItemViewType(int position) {
        SectionContainer<T> section = getItems().get(position);
        Object item = section.item;
        if(item != null) {
            return getViewType(item);
        }

        if(section.isOpen()) return 10;
        else return 11;
    }

    public boolean isExpandable() {
        return mExpandFlag == EXPAND_MANY || mExpandFlag == EXPAND_ONLY_ONE;
    }
}
