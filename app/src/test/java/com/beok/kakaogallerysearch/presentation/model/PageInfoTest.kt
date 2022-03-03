package com.beok.kakaogallerysearch.presentation.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PageInfoTest {

    private lateinit var pageInfo: PageInfo

    @Before
    fun setup() {
        pageInfo = PageInfo()
    }

    @Test
    fun `기존 데이터가 있는 상태에서_데이터를 최초로 불러올 때_페이징 정보를 초기화합니다`() {
        // given
        pageInfo.setup(isNext = true)

        // when
        pageInfo.setup(isNext = false)

        // then
        assertEquals(1, pageInfo.value)
        assertEquals(false, pageInfo.isEnd)
    }

    @Test
    fun `초기 데이터가 있는 상태에서_데이터를 추가로 불러올 때_페이징 정보를 업데이트합니다`() {
        // given
        pageInfo.setup(isNext = false)

        // when
        pageInfo.setup(isNext = true)

        // then
        assertEquals(2, pageInfo.value)
        assertEquals(false, pageInfo.isEnd)
    }

    @Test
    fun `기존 데이터가 있는 상태에서_isEnd값이 변경되면_페이징 정보를 업데이트합니다`() {
        // given
        pageInfo.setup(isNext = true)

        // when
        pageInfo.update(isEnd = true)

        // then
        assertTrue(pageInfo.isEnd)
    }
}
