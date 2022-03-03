package com.beok.kakaogallerysearch.presentation.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter(
    activity: FragmentActivity,
    private val fragmentGroup: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentGroup.size

    override fun createFragment(position: Int): Fragment = fragmentGroup[position]
}
