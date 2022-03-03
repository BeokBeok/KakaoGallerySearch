package com.beok.kakaogallerysearch.presentation.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.getOrAwaitValue
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
    fun `로딩 카운트를 더하면_로딩중입니다`() {
        // when
        loading.show()

        // then
        assertTrue(loading.isLoading.getOrAwaitValue())
    }

    @Test
    fun `로딩중일 때_로딩 카운트를 빼면_로딩중이 아닙니다`() {
        // given
        loading.show()

        // when
        loading.hide()

        // then
        assertFalse(loading.isLoading.getOrAwaitValue())
    }

    @Test
    fun `로딩중이 아닐때_카운트를 빼면_로딩중이 아닙니다`() {
        // given
        loading.show()
        loading.hide()

        // when
        loading.hide()

        // then
        assertFalse(loading.isLoading.getOrAwaitValue())
    }
}
