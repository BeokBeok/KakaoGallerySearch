package com.beok.kakaogallerysearch.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.beok.kakaogallerysearch.BR
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentMyBoxBinding
import com.beok.kakaogallerysearch.presentation.base.BaseAdapter
import com.beok.kakaogallerysearch.presentation.base.BaseFragment
import com.beok.kakaogallerysearch.presentation.main.model.Gallery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBoxFragment : BaseFragment<FragmentMyBoxBinding>(
    layoutResourceID = R.layout.fragment_my_box
) {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val adapter by lazy {
        BaseAdapter<Gallery>(
            layoutResourceId = R.layout.item_gallery,
            bindingID = BR.item,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.boxGroup.observe(viewLifecycleOwner) {
            binding.tvMyBoxEmpty.isVisible = it.isEmpty()
            adapter.addItem(it.last())
        }
    }

    private fun setupUI() {
        binding.rvMyBox.adapter = adapter
    }

    companion object {
        fun newInstance(): MyBoxFragment = MyBoxFragment()
    }
}
