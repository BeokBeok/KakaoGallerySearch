package com.beok.kakaogallerysearch.presentation

import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentSearchResultBinding
import com.beok.kakaogallerysearch.presentation.base.BaseFragment

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(
    layoutResourceID = R.layout.fragment_search_result
) {

    companion object {

        fun newInstance(): SearchResultFragment = SearchResultFragment()
    }
}
