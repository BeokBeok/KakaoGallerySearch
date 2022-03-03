package com.beok.kakaogallerysearch.presentation.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EventTest {

    private lateinit var event: Event<Boolean>

    @Before
    fun setup() {
        event = Event(content = true)
    }

    @Test
    fun `최초 접근 시_데이터를 전달합니다`() {
        // given

        // when
        val actual = event.getContentIfNotHandled() ?: false

        // then
        assertTrue(actual)
    }

    @Test
    fun `두 번 이상 접근 시_데이터를 전달하지 않습니다`() {
        // given
        event.getContentIfNotHandled()

        // when
        val actual = event.getContentIfNotHandled() ?: false

        // then
        assertFalse(actual)
    }
}
