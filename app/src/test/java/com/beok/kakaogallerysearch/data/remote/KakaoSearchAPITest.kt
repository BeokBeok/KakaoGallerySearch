package com.beok.kakaogallerysearch.data.remote

import com.beok.kakaogallerysearch.ISO8601Utils
import com.beok.kakaogallerysearch.data.model.ImageItem
import com.beok.kakaogallerysearch.data.model.ImageResponse
import com.beok.kakaogallerysearch.data.model.Meta
import com.beok.kakaogallerysearch.data.model.VideoItem
import com.beok.kakaogallerysearch.data.model.VideoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import java.io.File
import java.util.Date
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class KakaoSearchAPITest {

    private lateinit var server: MockWebServer
    private lateinit var api: KakaoSearchAPI

    @Before
    fun setup() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(Date::class.java, Rfc3339DateJsonAdapter())
                        .build()
                )
            )
            .baseUrl(server.url(""))
            .build()
            .create()
    }

    @Test
    fun `카카오 이미지를 검색합니다`() = runBlocking {
        // given
        val query = "설현"
        val response = MockResponse().setBody(File(PATH_SEARCH_IMAGE_JSON).readText())
            .setResponseCode(200)
        val expected = ImageResponse(
            documents = listOf(
                ImageItem(
                    collection = "news",
                    thumbnailUrl = "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp",
                    imageUrl = "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg",
                    width = 540,
                    height = 457,
                    displaySitename = "한국경제TV",
                    docUrl = "http://v.media.daum.net/v/20170621155930002",
                    datetime = Date(ISO8601Utils.fromString("2017-06-21T15:59:30.000+09:00"))
                )
            ),
            meta = Meta(totalCount = 422583, pageableCount = 3854, isEnd = false)
        )
        server.enqueue(response = response)

        // when
        val actual = api.searchImageBy(query = query)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `카카오 비디오를 검색합니다`() = runBlocking {
        // given
        val query = "AOA"
        val response = MockResponse().setBody(File(PATH_SEARCH_VCLIP_JSON).readText())
            .setResponseCode(200)
        val expected = VideoResponse(
            documents = listOf(
                VideoItem(
                    title = "AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’",
                    playTime = 185,
                    thumbnail = "https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F",
                    url = "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634",
                    datetime = Date(ISO8601Utils.fromString("2017-05-06T00:36:45+09:00")),
                    author = "_SBS"
                )
            ),
            meta = Meta(totalCount = 6033, pageableCount = 800, isEnd = false)
        )
        server.enqueue(response = response)

        // when
        val actual = api.searchVideoBy(query = query)

        // then
        assertEquals(expected, actual)
    }

    companion object {
        private const val PATH_SEARCH_IMAGE_JSON = "src/test/resources/search_image.json"
        private const val PATH_SEARCH_VCLIP_JSON= "src/test/resources/search_vclip.json"
    }
}
