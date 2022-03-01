package com.beok.kakaogallerysearch.domain.usecase

import app.cash.turbine.test
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchVideoUseCaseImplTest {
    private val repository: KakaoSearchRepository = mockk(relaxed = true)
    private lateinit var useCase: SearchUseCase<VideoChunk>

    @Before
    fun setup() {
        useCase = SearchVideoUseCaseImpl(repository = repository)
    }

    @Test
    fun `비디오를 검색합니다`() = runBlocking {
        val query = "설현"
        val videoChunk = VideoChunk(isEnd = false, videoGroup = listOf())
        val mockImageResponse = flow {
            emit(videoChunk)
        }

        every {
            repository.searchVideoBy(query = query)
        } returns mockImageResponse

        useCase.execute(query = query)
            .test {
                assertEquals(videoChunk, awaitItem())
                awaitComplete()
            }
    }
}
