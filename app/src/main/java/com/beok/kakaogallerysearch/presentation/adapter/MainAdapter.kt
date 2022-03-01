package com.beok.kakaogallerysearch.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.beok.kakaogallerysearch.presentation.MyBoxFragment
import com.beok.kakaogallerysearch.presentation.SearchResultFragment

class MainAdapter(
    activity: AppCompatActivity,
    private val fragmentGroup: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentGroup.size

    override fun createFragment(position: Int): Fragment = fragmentGroup[position]
}
