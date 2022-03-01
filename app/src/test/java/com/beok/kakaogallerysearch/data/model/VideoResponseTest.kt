package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.domain.model.Image
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class VideoResponseTest {

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
        val actual = moshi.adapter(VideoResponse::class.java)
            .fromJson(VIDEOS_RESPONSE_JSON)

        assertNotNull(actual?.meta)
        assertNotNull(actual?.documents)
        assertNotEquals(emptyList<VideoItem>(), actual?.documents)
    }

    @Test
    fun `domain 모델로 변환합니다`() {
        val response = VideoResponse(documents = listOf(), meta = null)

        val actual = response.toDomain()

        actual.run {
            assertEquals(false, isEnd)
            assertEquals(emptyList<Image>(), videoGroup)
        }
    }

    companion object {
        private val VIDEOS_RESPONSE_JSON =
            """
                {
                    "meta": {    
                        "total_count": 6033,
                        "pageable_count": 800,
                        "is_end": false
                    },
                    "documents": [
                        {
                            "title": "AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’",
                            "play_time": 185,
                            "thumbnail": "https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F",
                            "url": "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634",
                            "datetime": "2017-05-06T00:36:45+09:00",
                            "author": "_SBS"
                        }
                    ]
                }    
            """.trimIndent()
    }
}
