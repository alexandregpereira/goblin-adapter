package com.bano.goblin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 * Created by Alexandre on 30/05/2017.
 */

abstract class BaseBottomSpaceAdapter<T, E : ViewDataBinding>(
    context: Context,
    items: MutableList<T>,
    layoutRes: Int,
    listener: ((T) -> Unit)? = null
) : BaseAdapter<T, E>(context, items, layoutRes, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.ViewHolder<T, E> {
        return if (viewType == TYPE_ITEM) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<E>(layoutInflater, layoutRes, parent, false)
            BaseAdapter.ViewHolder(binding, listener)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_bottom_space, parent, false)
            BaseAdapter.ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: BaseAdapter.ViewHolder<T, E>, position: Int) {
        if (getItemViewType(position) == TYPE_BOTTOM) return
        super.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isPositionBottom(position)) TYPE_BOTTOM else TYPE_ITEM

    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    private fun isPositionBottom(position: Int): Boolean {
        return items.size == position
    }

    companion object {
        private const val TYPE_ITEM = 1
        private const val TYPE_BOTTOM = 2
    }
}
