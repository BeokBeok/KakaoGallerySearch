package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImagesResponseTest {

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
        val actual = moshi.adapter(ImagesResponse::class.java)
            .fromJson(IMAGES_RESPONSE_JSON)

        assertNotNull(actual?.meta)
        assertNotNull(actual?.documents)
        assertNotEquals(emptyList<ImageItem>(), actual?.documents)
    }

    companion object {
        private val IMAGES_RESPONSE_JSON =
            """
                {
                    "meta": {
                        "total_count": 422583,
                        "pageable_count": 3854,
                        "is_end": false
                    },
                    "documents": [
                        {
                            "collection": "news",
                            "thumbnail_url": "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp",
                            "image_url": "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg",
                            "width": 540,
                            "height": 457,
                            "display_sitename": "한국경제TV",
                            "doc_url": "http://v.media.daum.net/v/20170621155930002",
                            "datetime": "2017-06-21T15:59:30.000+09:00"
                        }
                    ]
                }
            """.trimIndent()
    }
}