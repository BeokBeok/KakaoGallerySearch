package com.beok.kakaogallerysearch.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.beok.kakaogallerysearch.presentation.model.ClickAction

class BaseViewHolder<T> private constructor(
    itemView: View,
    private val binding: ViewDataBinding,
    private val bindingID: Int,
    private val clickAction: ClickAction<T>?
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: T?) {
        if (item == null) return
        binding.setVariable(bindingID, item)

        clickAction?.run {
            binding.setVariable(bindingID, onClick)
        }
        binding.executePendingBindings()
    }

    companion object {
        fun <T> create(
            parent: ViewGroup,
            @LayoutRes layoutResourceID: Int,
            bindingID: Int,
            clickAction: ClickAction<T>?
        ): BaseViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                inflater,
                layoutResourceID,
                parent,
                false
            )

            return BaseViewHolder(
                itemView = binding.root,
                binding = binding,
                bindingID = bindingID,
                clickAction = clickAction
            )
        }
    }
}
