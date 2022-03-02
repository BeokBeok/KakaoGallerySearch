package com.beok.kakaogallerysearch.presentation.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter<T>(
    @LayoutRes private val layoutResourceId: Int,
    private val bindingID: Int,
    private val viewModel: Map<Int, ViewModel> = mapOf()
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val itemGroup = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(parent, layoutResourceId, bindingID, viewModel)

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
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
