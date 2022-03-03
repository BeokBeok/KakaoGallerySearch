package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.domain.model.Image
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoResponseTest {

    @Test
    fun `Response를_domain 모델로 변환합니다`() {
        // given
        val response = VideoResponse(documents = listOf(), meta = null)

        // when
        val actual = response.toDomain()

        // then
        actual.run {
            assertEquals(false, isEnd)
            assertEquals(emptyList<Image>(), videoGroup)
        }
    }
}
