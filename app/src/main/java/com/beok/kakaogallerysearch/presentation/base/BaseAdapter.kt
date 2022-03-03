package com.beok.kakaogallerysearch.presentation.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.beok.kakaogallerysearch.presentation.model.ClickAction

class BaseAdapter<T>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingID: Int,
    private val clickAction: ClickAction<T>? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private val itemGroup = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
        BaseViewHolder.create(
            parent = parent,
            layoutResourceID = layoutResourceId,
            bindingID = bindingID,
            clickAction = clickAction
        )

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(item = itemGroup[position])
    }

    override fun getItemCount(): Int = itemGroup.size

    @SuppressLint("NotifyDataSetChanged")
    fun replaceItems(item: List<T>) {
        itemGroup.run {
            clear()
            addAll(item)
        }
        notifyDataSetChanged()
    }
}
