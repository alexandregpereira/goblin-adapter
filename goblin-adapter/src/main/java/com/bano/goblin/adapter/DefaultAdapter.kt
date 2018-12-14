package com.bano.goblin.adapter

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

/**
 * Created by bk_alexandre.pereira on 05/12/2017.
 *
 */

abstract class DefaultAdapter<T, E : ViewDataBinding, V : RecyclerView.ViewHolder>(
    context: Context? = null,
    items: MutableList<T>,
    internal val layoutRes: Int,
    var listener: ((T) -> Unit)? = null
) : RecyclerView.Adapter<V>() {

    private var mOnBottomItemListener: (() -> Unit)? = null
    private var mHandler: Handler? = null
    val resources: Resources?
    protected val mItems: MutableList<T> = items
    private var lastPageBottomPosition: Int = 0
    private var pageSize: Int = 0

    open var items: List<T>
        get() = mItems
        set(items) = setItemsInRange(0, mItems.size, items)

    protected abstract fun onBindViewHolder(e: E, t: T)

    init {
        resources = context?.resources
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        if (position > lastPageBottomPosition && isPagePositionBottom(position) && mOnBottomItemListener != null && mHandler != null) {
            lastPageBottomPosition = position
            val onBottomItemListener = mOnBottomItemListener
            mHandler?.post { onBottomItemListener?.invoke() }
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun replace(t: T) {
        val i = mItems.indexOf(t)
        if (i >= 0) {
            mItems[i] = t
            notifyItemChanged(i, t)
        }
    }

    fun remove(t: T) {
        val i = mItems.indexOf(t)
        if (i < 0) return
        mItems.removeAt(i)
        notifyItemRemoved(i)
    }

    fun setItem(t: T) {
        val i = mItems.indexOf(t)
        if (i >= 0) {
            mItems[i] = t
            notifyItemChanged(i, t)
        } else {
            this.mItems.add(t)
            notifyItemInserted(mItems.size - 1)
        }
    }

    fun setItemsInRange(startIndex: Int, toIndex: Int, items: List<T>) {
        var index = startIndex
        for (item in items) {
            setItemAtIndex(index++, item)
        }

        val allItems = mItems

        val toIndexReal: Int
        toIndexReal = if (allItems.size - startIndex < toIndex) {
            startIndex + allItems.size - startIndex
        } else
            allItems.size
        if (toIndexReal <= startIndex) return
        val itemsInRange = allItems.subList(startIndex, toIndexReal)
        val itemsToRemove = ArrayList<T>()
        if (items.size < itemsInRange.size) {
            for (item in itemsInRange) {
                val i = items.indexOf(item)
                if (i < 0) {
                    itemsToRemove.add(item)
                }
            }

            for (itemToRemove in itemsToRemove) {
                remove(itemToRemove)
            }
        }
    }

    open fun setItemAtIndex(index: Int, t: T) {
        val i = mItems.indexOf(t)
        if (i >= 0) {
            if (i != index) {
                mItems.removeAt(i)
                notifyItemRemoved(i)
                val indexReal = if (index > mItems.size) mItems.size else index
                mItems.add(indexReal, t)
                notifyItemInserted(indexReal)
                return
            }
            mItems[i] = t
            notifyItemChanged(i, t)
        } else {
            val indexReal = if (index > mItems.size) mItems.size else index
            this.mItems.add(indexReal, t)
            notifyItemInserted(indexReal)
        }
    }

    fun addItem(position: Int, t: T) {
        this.mItems.add(position, t)
        notifyItemInserted(position)
    }

    fun setOnBottomItemListener(pageSize: Int, handler: Handler, onBottomItemListener: () -> Unit) {
        this.pageSize = pageSize
        mHandler = handler
        mOnBottomItemListener = onBottomItemListener
    }

    fun clearLastPageBottomPosition() {
        lastPageBottomPosition = 0
    }

    private fun isPagePositionBottom(position: Int): Boolean {
        if (pageSize == 0) return false
        val currentPosition = position + 1
        return currentPosition % (pageSize - 2) == 0
    }
}
