package com.beok.kakaogallerysearch.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.beok.kakaogallerysearch.BR
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentMyBoxBinding
import com.beok.kakaogallerysearch.presentation.base.BaseFragment
import com.beok.kakaogallerysearch.presentation.base.BaseListAdapter
import com.beok.kakaogallerysearch.presentation.model.Gallery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBoxFragment : BaseFragment<FragmentMyBoxBinding>(
    layoutResourceID = R.layout.fragment_my_box
) {

    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        binding.rvMyBox.adapter = BaseListAdapter(
            layoutResourceID = R.layout.item_gallery,
            bindingID = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel),
            diffUtil = object : DiffUtil.ItemCallback<Gallery>() {
                override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery): Boolean =
                    oldItem.datetime == newItem.datetime

                override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery): Boolean =
                    oldItem == newItem
            }
        ).apply {
            viewModel.boxGroup.observe(viewLifecycleOwner) {
                submitList(it)
            }
        }
    }

    companion object {
        fun newInstance(): MyBoxFragment = MyBoxFragment()
    }
}
