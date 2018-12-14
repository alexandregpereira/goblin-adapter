package com.bano.goblin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * A base Recycler View adapter that uses DataBinding
 */

@Suppress("UNCHECKED_CAST", "SENSELESS_COMPARISON")
abstract class BaseAdapter<T, E : ViewDataBinding>(
    context: Context? = null,
    items: MutableList<T> = mutableListOf(),
    layoutRes: Int,
    listener: ((T) -> Unit)? = null
) : DefaultAdapter<T, E, BaseAdapter.ViewHolder<T, E>>(
    context = context,
    items = items,
    layoutRes = layoutRes,
    listener = listener
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T, E> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<E>(layoutInflater, layoutRes, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder<T, E>, position: Int) {
        super.onBindViewHolder(holder, position)
        val t = mItems[position]
        holder.binding!!.root.tag = t
        this.onBindViewHolder(holder.binding, t)
    }

    override fun onBindViewHolder(holder: ViewHolder<T, E>, position: Int, payloads: List<Any>) {
        if (payloads != null && !payloads.isEmpty()) {
            // update the specific view
            val t = payloads[0] as T
            holder.binding!!.root.tag = t
            this.onBindViewHolder(holder.binding, t)
        } else {
            // I have already overridden  the other onBindViewHolder(ViewHolder, int)
            // The method with 3 arguments is being called before the method with 2 args.
            // so calling super will call that method with 2 arguments.
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    class ViewHolder<T, E : ViewDataBinding> : RecyclerView.ViewHolder {
        val binding: E?

        constructor(binding: E, listener: ((T) -> Unit)?) : super(binding.root) {
            this.binding = binding
            if (listener != null) {
                binding.root.setOnClickListener { view ->
                    val t = view.tag as T
                    listener.invoke(t)
                }
            }
        }

        internal constructor(view: View) : super(view) {
            this.binding = null
        }
    }
}
