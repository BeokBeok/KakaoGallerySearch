package com.beok.kakaogallerysearch.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.beok.kakaogallerysearch.R
import com.beok.kakaogallerysearch.databinding.ActivityMainBinding
import com.beok.kakaogallerysearch.presentation.main.adapter.MainAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupUI()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun setupUI() {
        binding?.run {
            vpMain.adapter = MainAdapter(
                activity = this@MainActivity,
                fragmentGroup = listOf(
                    SearchResultFragment.newInstance(),
                    MyBoxFragment.newInstance()
                )
            )
            val tabTitleGroup = listOf(
                getString(R.string.search_result),
                getString(R.string.my_box)
            )
            TabLayoutMediator(tlMain, vpMain) { tab, position ->
                tab.text = tabTitleGroup[position]
            }.attach()
        }
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
    }
}
