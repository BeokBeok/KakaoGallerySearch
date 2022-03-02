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
    // adapter에 ViewModel을 넘기는게 엄청 일반적이진 않아서, 이것도 질문 들어오기 좋아보이네요.
    // 어떤 이유로 사용하시고 있는지, 장점은 무엇이 있는지 정리해두시면 좋을것 같아요.
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val itemGroup = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder.create(parent, layoutResourceId, bindingID, viewModel)

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
