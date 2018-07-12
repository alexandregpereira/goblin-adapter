package com.bano.goblin.adapter;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class GoblinAdapter extends BaseAdapter<GoblinAdapter.Item, ViewDataBinding> {

    public GoblinAdapter(@NonNull List<Item> items) {
        super(items, 0, null);
    }

    @Override
    protected void onBindViewHolder(ViewDataBinding viewDataBinding, Item item) {

    }

    public static class Item {
        public final int id;

        public Item(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return id == item.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
}
