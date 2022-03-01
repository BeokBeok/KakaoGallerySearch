package com.beok.kakaogallerysearch.presentation

import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.FragmentMyBoxBinding
import com.beok.kakaogallerysearch.presentation.base.BaseFragment

class MyBoxFragment : BaseFragment<FragmentMyBoxBinding>(
    layoutResourceID = R.layout.fragment_my_box
) {

    companion object {
        fun newInstance(): MyBoxFragment = MyBoxFragment()
    }
}
