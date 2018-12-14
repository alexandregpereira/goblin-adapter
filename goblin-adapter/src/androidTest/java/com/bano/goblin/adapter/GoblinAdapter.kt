package com.bano.goblin.adapter

import androidx.databinding.ViewDataBinding
import java.util.Objects

class GoblinAdapter(items: MutableList<Item>) : BaseAdapter<GoblinAdapter.Item, ViewDataBinding>(items = items, layoutRes = 0, listener = null) {

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, item: Item) {

    }

    class Item(val id: Int) {

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false
            val item = o as Item?
            return id == item!!.id
        }

        override fun hashCode(): Int {
            return Objects.hash(id)
        }
    }
}
