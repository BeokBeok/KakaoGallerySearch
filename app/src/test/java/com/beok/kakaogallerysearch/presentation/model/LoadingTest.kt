package com.beok.kakaogallerysearch.presentation.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.getOrAwaitValue
import com.beok.kakaogallerysearch.presentation.model.Loading
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoadingTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var loading: Loading

    @Before
    fun setup() {
        loading = Loading()
    }

    @Test
    fun `로딩 카운트를 더하면 로딩중입니다`() {
        loading.show()

        assertTrue(loading.isLoadingCount.getOrAwaitValue())
    }

    @Test
    fun `로딩 카운트를 빼서 0이 되면 로딩중이 아닙니다`() {
        loading.show()
        loading.hide()

        assertFalse(loading.isLoadingCount.getOrAwaitValue())
    }
}
