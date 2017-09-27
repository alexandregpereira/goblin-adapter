package com.bano.goblin.adapter;

import java.util.ArrayList;

/**
 * This class is used on BaseAccordionAdapter class
 */

public class AccordionContainer<T> {

    public static final int TYPE_ITEM = 1;
    public static final int TYPE_CATEGORY = 2;

    public final String category;
    public final T t;
    public final ArrayList<T> tList;
    public final int type;
    private int position;
    private boolean open;

    public AccordionContainer(String category, ArrayList<T> tList) {
        this.tList = tList;
        this.type = TYPE_CATEGORY;
        this.category = category;
        this.t = null;
    }

    public AccordionContainer(String category, ArrayList<T> tList, int position) {
        this.tList = tList;
        this.type = TYPE_CATEGORY;
        this.category = category;
        this.t = null;
        this.position = position;
    }

    public AccordionContainer(T t) {
        this.category = null;
        this.t = t;
        this.tList = null;
        this.type = TYPE_ITEM;
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

        AccordionContainer<?> that = (AccordionContainer<?>) o;

        return t != null ? t.equals(that.t) : that.t == null;
    }

    @Override
    public int hashCode() {
        return t != null ? t.hashCode() : 0;
    }
}
