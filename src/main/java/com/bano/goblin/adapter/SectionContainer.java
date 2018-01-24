package com.bano.goblin.adapter;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * This class is used on BaseAccordionAdapter class
 */

public class SectionContainer<T> {

    @IntDef(value = {TYPE_ITEM, TYPE_CATEGORY})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ViewTypeFlags {}

    public static final int TYPE_ITEM = 1;
    public static final int TYPE_CATEGORY = 2;

    public final T category;
    public final Object item;
    public final List<Object> itemList;
    @ViewTypeFlags
    public final int type;
    private int position;
    private boolean open;

    public SectionContainer(T category, List<Object> itemList) {
        this.itemList = itemList;
        this.type = TYPE_CATEGORY;
        this.category = category;
        this.item = null;
    }

    public SectionContainer(T category, List<Object> itemList, int position) {
        this.itemList = itemList;
        this.type = TYPE_CATEGORY;
        this.category = category;
        this.item = null;
        this.position = position;
    }

    public SectionContainer(Object item) {
        this.category = null;
        this.item = item;
        this.itemList = null;
        this.type = TYPE_ITEM;
    }

    public SectionContainer(Object item, int position) {
        this.category = null;
        this.item = item;
        this.itemList = null;
        this.type = TYPE_ITEM;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SectionContainer<?> that = (SectionContainer<?>) o;

        return category != null ? category.equals(that.category) : that.category == null && (item != null ? item.equals(that.item) : that.item == null);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }
}
