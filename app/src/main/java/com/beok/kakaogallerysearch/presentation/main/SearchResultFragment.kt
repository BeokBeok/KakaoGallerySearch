package com.beok.kakaogallerysearch.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.beok.kakaogallerysearch.BR
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentSearchResultBinding
import com.beok.kakaogallerysearch.presentation.base.BaseAdapter
import com.beok.kakaogallerysearch.presentation.base.BaseFragment
import com.beok.kakaogallerysearch.presentation.ext.launchAndRepeatOnLifecycle
import com.beok.kakaogallerysearch.presentation.ext.textChanges
import com.beok.kakaogallerysearch.presentation.model.ClickAction
import com.beok.kakaogallerysearch.presentation.main.model.Gallery
import com.beok.kakaogallerysearch.presentation.main.model.MyBoxStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot

@AndroidEntryPoint
class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    layoutResourceID = R.layout.fragment_search_result
) {
    private val viewModel by activityViewModels<SearchViewModel>()
    private val adapter by lazy {
        BaseAdapter<Gallery>(
            layoutResourceId = R.layout.item_gallery,
            bindingID = BR.item,
            clickAction = ClickAction(bindingID = BR.onClick) {
                viewModel.onClickForSave(item = it)
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupUI()
        setupListener()
        setupObserver()
        showContent()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
    }

    private fun setupUI() {
        binding.rvSearchResult.adapter = adapter
    }

    private fun setupObserver() = with(viewModel) {
        galleryGroup.observe(viewLifecycleOwner) {
            adapter.replaceItems(it)
        }
        error.observe(viewLifecycleOwner) {
            val error = it.getContentIfNotHandled() ?: return@observe
            Toast.makeText(
                requireContext(),
                error.message ?: getString(R.string.error_occurred_retry),
                Toast.LENGTH_SHORT
            ).show()
        }
        myBoxStatus.observe(viewLifecycleOwner) {
            val status = it.getContentIfNotHandled() ?: return@observe
            val message = when (status) {
                MyBoxStatus.Added -> getString(R.string.added_box)
                MyBoxStatus.AlreadyAdded -> getString(R.string.already_box)
            }
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
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
                .debounce(500)
                .collectLatest {
                    it?.toString()?.let { keyword ->
                        loadContent(isNext = false, keyword = keyword)
                    }
                }
        }
    }

    private fun loadContent(isNext: Boolean, keyword: String) {
        viewModel.searchGallery(isNext = isNext, query = keyword)
    }

    companion object {
        fun newInstance(): SearchResultFragment = SearchResultFragment()
    }
}
