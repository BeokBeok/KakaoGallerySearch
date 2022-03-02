package com.beok.kakaogallerysearch.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter(
    activity: FragmentActivity,
    // FragmentStateAdapter에서 받는게 'FragmentActivity' 이므로 그 타입을 쓰는게 좋아보여요
    private val fragmentGroup: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragmentGroup.size

    override fun createFragment(position: Int): Fragment = fragmentGroup[position]
}
