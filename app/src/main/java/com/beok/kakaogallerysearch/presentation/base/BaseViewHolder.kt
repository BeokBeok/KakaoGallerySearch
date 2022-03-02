package com.beok.kakaogallerysearch.presentation.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder private constructor(
    itemView: View,
    private val binding: ViewDataBinding,
    private val bindingID: Int,
    private val viewModel: Map<Int, ViewModel>
) : RecyclerView.ViewHolder(itemView) {

    // 이렇게 하면, binding NonNull하게 하고, 외부에서는 create만 쓰도록 할 수 있을것 같아요. 이것도 제안입니다!

    fun bind(item: Any?) {
        if (item == null) return
        binding.setVariable(bindingID, item)

        for (key in viewModel.keys) {
            binding.setVariable(key, viewModel[key])
        }
        binding.executePendingBindings()
    }

    companion object {

        fun create(
            parent: ViewGroup,
            @LayoutRes layoutResourceID: Int,
            bindingID: Int,
            viewModel: Map<Int, ViewModel>
        ): BaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutResourceID, parent, false)

            return BaseViewHolder(
                itemView = binding.root,
                binding = binding,
                bindingID = bindingID,
                viewModel = viewModel
            )
        }
    }
}
