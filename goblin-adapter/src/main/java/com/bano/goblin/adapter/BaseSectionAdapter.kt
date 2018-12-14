package com.bano.goblin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

/**
 * Created by Alexandre Pereira on 10/10/2017.
 *
 * Provides an recycler view adapter with section expanded
 */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
abstract class BaseSectionAdapter<T>(
        items: MutableList<SectionContainer<T>>,
        private val mExpandFlag: ExpandFlag = ExpandFlag.NOT_EXPANDABLE,
        listener: ((SectionContainer<T>) -> Unit)? = null
) : BaseAdapter<SectionContainer<T>, ViewDataBinding>(null, items, 0, listener) {

    private var mPreviousAccordionOpened: SectionContainer<T>? = null

    val isExpandable: Boolean
        get() = mExpandFlag == ExpandFlag.EXPAND_MANY || mExpandFlag == ExpandFlag.EXPAND_ONLY_ONE

    protected abstract fun getViewDataBinding(
        viewType: Int,
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ViewDataBinding

    protected abstract fun getViewType(item: Any): Int
    protected abstract fun bindData(sectionContainer: SectionContainer<T>, item: Any?, viewDataBinding: ViewDataBinding)

    override fun onBindViewHolder(viewDataBinding: ViewDataBinding, sectionContainer: SectionContainer<T>) {
        val item = sectionContainer.item
        bindData(sectionContainer, item, viewDataBinding)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseAdapter.ViewHolder<SectionContainer<T>, ViewDataBinding> {
        val layoutInflater = LayoutInflater.from(parent.context)

        val accordionListener: (SectionContainer<T>?) -> Unit = accordionListener@{ sectionContainer ->
            if (sectionContainer == null) return@accordionListener
            if (isExpandable && sectionContainer.type == SectionContainer.TYPE_CATEGORY) {
                if (sectionContainer.isOpen) {
                    collapse(sectionContainer)
                } else {
                    val previousAccordionOpened = mPreviousAccordionOpened
                    if (mExpandFlag == ExpandFlag.EXPAND_ONLY_ONE && previousAccordionOpened != null) {
                        collapse(previousAccordionOpened)
                    }
                    expand(sectionContainer)
                }
            } else if (listener != null) {
                listener?.invoke(sectionContainer)
            }
        }

        val binding = getViewDataBinding(viewType, layoutInflater, parent)
        return BaseAdapter.ViewHolder(binding, accordionListener)
    }

    private fun expand(sectionContainer: SectionContainer<T>) {
        mPreviousAccordionOpened = sectionContainer
        sectionContainer.isOpen = true
        replace(sectionContainer)
        var position = sectionContainer.position + 1
        for (item in sectionContainer.itemList) {
            addItem(position, SectionContainer(item))
            ++position
        }

        for (i in position until items.size) {
            items[i].position = i
        }
    }

    private fun collapse(sectionContainer: SectionContainer<T>) {
        sectionContainer.isOpen = false
        replace(sectionContainer)
        for (item in sectionContainer.itemList) {
            remove(SectionContainer(item))
        }

        for (i in sectionContainer.position + 1 until items.size) {
            items[i].position = i
        }
    }

    override fun getItemViewType(position: Int): Int {
        val section = items[position]
        val item = section.item
        if (item != null) {
            return getViewType(item)
        }

        return if (section.isOpen)
            10
        else
            11
    }

    enum class ExpandFlag {
        EXPAND_ONLY_ONE, EXPAND_MANY, NOT_EXPANDABLE
    }
}
