package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.ISO8601Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ImageItemTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    @Test
    fun `Json을 Response로 변환합니다`() {
        val actual = moshi.adapter(ImageItem::class.java)
            .fromJson(IMAGE_ITEM_JSON)

        assertEquals(COLLECTION, actual?.collection)
        assertEquals(THUMBNAIL_URL, actual?.thumbnailUrl)
        assertEquals(IMAGE_URL, actual?.imageUrl)
        assertEquals(WIDTH, actual?.width)
        assertEquals(HEIGHT, actual?.height)
        assertEquals(DISPLAY_SITENAME, actual?.displaySitename)
        assertEquals(DOC_URL, actual?.docUrl)
        assertEquals(ISO8601Utils.fromString(DATETIME), actual?.datetime?.time)
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
        private val IMAGE_ITEM_JSON =
            """
                {
                    "collection": "$COLLECTION",
                    "thumbnail_url": "$THUMBNAIL_URL",
                    "image_url": "$IMAGE_URL",
                    "width": "$WIDTH",
                    "height": "$HEIGHT",
                    "display_sitename": "$DISPLAY_SITENAME",
                    "doc_url": "$DOC_URL",
                    "datetime": "$DATETIME"
                }
            """.trimIndent()
    }
}
