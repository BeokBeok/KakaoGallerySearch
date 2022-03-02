package com.beok.kakaogallerysearch.presentation

import android.os.Bundle
import android.view.View
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
import com.beok.kakaogallerysearch.presentation.model.Gallery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot

@AndroidEntryPoint
class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    layoutResourceID = R.layout.fragment_search_result
) {

    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupUI()
        setupListener()
        showContent()
    }

    private fun setupBinding() {
        binding.viewModel = viewModel
    }

    private fun setupUI() {
        binding.rvSearchResult.adapter = BaseAdapter<Gallery>(
            layoutResourceId = R.layout.item_gallery,
            bindingID = BR.item,
            viewModel = mapOf(BR.viewModel to viewModel)
        ).apply {
            viewModel.galleryGroup.observe(viewLifecycleOwner) {
                replaceItems(it)
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
            // FlowPreview < lint 잡히는거 그레들 어디에 옵션 넣으면 됐던거 같은데, 이런거 넣어주면 좋아할것 같아요.
            binding.etSearchResult
                .textChanges()
                .filterNot(CharSequence?::isNullOrBlank)
                .debounce(300)
                .collectLatest {
                    it?.toString()?.let { keyword ->
                        loadContent(isNext = false, keyword = keyword)
                    }
                }
            // collectLatest 동작도 면접 질문으로 딱이네요. 근데 collectLatest가 필요한가요? debounce만으로는 안되나요?
        }
    }

    private fun loadContent(isNext: Boolean, keyword: String) {
        viewModel.searchGallery(isNext, keyword)
    }

    companion object {

        fun newInstance(): SearchResultFragment = SearchResultFragment()
    }
}
