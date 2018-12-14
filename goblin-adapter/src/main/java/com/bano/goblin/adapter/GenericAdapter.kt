package com.bano.goblin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

/**
 * Created by Alexandre Pereira on 10/10/2017.
 *
 * Provides an recycler view generic adapter
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
abstract class GenericAdapter<T : Any>(
    items: MutableList<T> = mutableListOf(),
    listener: ((T) -> Unit)? = null
) : BaseAdapter<T, ViewDataBinding>(null, items, 0, listener) {

    protected abstract fun getViewDataBinding(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ViewDataBinding

    protected abstract fun getViewType(item: T): Int
    protected abstract fun bindData(item: T, viewDataBinding: ViewDataBinding)

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, item: T) {
        bindData(item, viewDataBinding)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAdapter.ViewHolder<T, ViewDataBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = getViewDataBinding(viewType, layoutInflater, parent)
        return BaseAdapter.ViewHolder(binding, listener)
    }

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return getViewType(item)
    }
}
