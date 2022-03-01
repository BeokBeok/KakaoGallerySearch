package com.beok.kakaogallerysearch.data

import com.beok.kakaogallerysearch.data.remote.KakaoSearchRemoteDataSource
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class KakaoSearchRepositoryImplTest {

    private val remoteDataSource: KakaoSearchRemoteDataSource = mockk(relaxed = true)
    private lateinit var repository: KakaoSearchRepository

    @Before
    fun setup() {
        repository = KakaoSearchRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Test
    fun `이미지를 검색합니다`() {
        val query = "설현"
        repository.searchImageBy(query = query)
        verify {
            remoteDataSource.searchImageBy(query = query)
        }
    }

    @Test
    fun `비디오를 검색합니다`() {
        val query = "AOA"
        repository.searchVideoBy(query = query)
        verify {
            remoteDataSource.searchVideoBy(query = query)
        }
    }
}
