package com.beok.kakaogallerysearch.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
        setupListener()
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

    private fun setupListener() {
        binding.rvSearchResult.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy < 0) return
                    val bottomDirection = 1
                    if (!recyclerView.canScrollVertically(bottomDirection)) {
                        loadContent(isNext = true, keyword = binding.etSearchResult.text.toString())
                    }
                }
            }
        )
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
                        loadContent(isNext = false, keyword = keyword)
                    }
                }
        }
    }

    private fun loadContent(isNext: Boolean, keyword: String) {
        viewModel.setupPageInfo(isNext)
        viewModel.searchByImage(keyword)
        viewModel.searchByVideo(keyword)
    }

    companion object {

        fun newInstance(): SearchResultFragment = SearchResultFragment()
    }
}
