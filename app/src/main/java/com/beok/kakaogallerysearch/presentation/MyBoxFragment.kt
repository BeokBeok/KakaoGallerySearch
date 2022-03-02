package com.beok.kakaogallerysearch.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.beok.kakaogallerysearch.BR
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentMyBoxBinding
import com.beok.kakaogallerysearch.presentation.base.BaseAdapter
import com.beok.kakaogallerysearch.presentation.base.BaseFragment
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
        binding.rvMyBox.adapter = BaseAdapter<Gallery>(
            layoutResourceId = R.layout.item_gallery,
            bindingID = BR.item,
        ).apply {
            viewModel.boxGroup.observe(viewLifecycleOwner) {
                replaceItems(it)
            }
        }
    }

    companion object {
        fun newInstance(): MyBoxFragment = MyBoxFragment()
    }
}
