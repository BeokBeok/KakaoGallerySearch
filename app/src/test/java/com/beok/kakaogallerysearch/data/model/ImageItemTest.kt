package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.ISO8601Utils
import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageItemTest {

    @Test
    fun `Response를_domain 모델로 변환합니다`() {
        // given
        val response = ImageItem(
            collection = COLLECTION,
            thumbnailUrl = THUMBNAIL_URL,
            imageUrl = IMAGE_URL,
            width = WIDTH,
            height = HEIGHT,
            displaySitename = DISPLAY_SITENAME,
            docUrl = DOC_URL,
            datetime = Date(ISO8601Utils.fromString(DATETIME))
        )

        // when
        val actual = response.toDomain()

        // then
        actual.run {
            assertEquals(COLLECTION, collection)
            assertEquals(THUMBNAIL_URL, thumbnailUrl)
            assertEquals(IMAGE_URL, imageUrl)
            assertEquals(WIDTH, width)
            assertEquals(HEIGHT, height)
            assertEquals(DISPLAY_SITENAME, displaySitename)
            assertEquals(DOC_URL, docUrl)
            assertEquals(ISO8601Utils.fromString(DATETIME), datetime.time)
        }
    }

    companion object {
        private const val COLLECTION = "news"
        private const val THUMBNAIL_URL = "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp"
        private const val IMAGE_URL = "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg"
        private const val WIDTH = 540
        private const val HEIGHT = 457
        private const val DISPLAY_SITENAME = "한국경제TV"
        private const val DOC_URL = "http://v.media.daum.net/v/20170621155930002"
        private const val DATETIME = "2017-06-21T15:59:30.000+09:00"
    }
}
