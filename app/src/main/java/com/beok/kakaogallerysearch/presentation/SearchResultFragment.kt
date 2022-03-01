package com.beok.kakaogallerysearch.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import com.beok.kakaogallerysearch.BR
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentSearchResultBinding
import com.beok.kakaogallerysearch.presentation.ext.launchAndRepeatOnLifecycle
import com.beok.kakaogallerysearch.presentation.ext.textChanges
import com.beok.kakaogallerysearch.presentation.base.BaseFragment
import com.beok.kakaogallerysearch.presentation.base.BaseListAdapter
import com.beok.kakaogallerysearch.presentation.model.Gallery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot

@AndroidEntryPoint
class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    layoutResourceID = R.layout.fragment_search_result
) {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        showContent()
    }

    private fun setupUI() {
        binding.rvSearchResult.adapter = BaseListAdapter(
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
            viewModel.galleryGroup.observe(viewLifecycleOwner) {
                submitList(it)
            }
        }
    }

    private fun showContent() {
        launchAndRepeatOnLifecycle(
            scope = lifecycleScope,
            owner = viewLifecycleOwner
        ) {
            binding.etSearchResult
                .textChanges()
                .filterNot(CharSequence?::isNullOrBlank)
                .debounce(300)
                .collectLatest {
                    it?.toString()?.let { keyword ->
                        viewModel.setupPageInfo()
                        viewModel.searchByImage(keyword)
                        viewModel.searchByVideo(keyword)
                    }
                }
        }
    }

    companion object {

        fun newInstance(): SearchResultFragment = SearchResultFragment()
    }
}
