package com.beok.kakaogallerysearch.data.remote

import app.cash.turbine.test
import com.beok.kakaogallerysearch.data.model.ImageResponse
import com.beok.kakaogallerysearch.data.model.VideoResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class KakaoSearchRemoteDataSourceImplTest {

    private val searchAPI: KakaoSearchAPI = mockk()
    private lateinit var dataSource: KakaoSearchRemoteDataSource

    @Before
    fun setup() {
        dataSource = KakaoSearchRemoteDataSourceImpl(api = searchAPI)
    }

    @Test
    fun `이미지를 검색합니다`() = runBlocking {
        // given
        val query = "설현"
        val mockResponse: ImageResponse = mockk()
        coEvery {
            searchAPI.searchImageBy(query = query)
        } returns mockResponse

        // when
        dataSource.searchImageBy(query = query)
            .test {
                // then
                assertEquals(mockResponse, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `비디오를 검색합니다`() = runBlocking {
        // given
        val query = "AOA"
        val mockResponse: VideoResponse = mockk()
        coEvery {
            searchAPI.searchVideoBy(query = query)
        } returns mockResponse

        // when
        dataSource.searchVideoBy(query = query)
            .test {
                // then
                assertEquals(mockResponse, awaitItem())
                awaitComplete()
            }
    }
}
