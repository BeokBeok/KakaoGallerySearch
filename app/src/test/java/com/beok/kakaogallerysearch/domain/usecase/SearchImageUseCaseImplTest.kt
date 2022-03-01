package com.beok.kakaogallerysearch.domain.usecase

import app.cash.turbine.test
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchImageUseCaseImplTest {

    private val repository: KakaoSearchRepository = mockk(relaxed = true)
    private lateinit var useCase: SearchUseCase<ImageChunk>

    @Before
    fun setup() {
        useCase = SearchImageUseCaseImpl(repository = repository)
    }

    @Test
    fun `이미지를 검색합니다`() = runBlocking {
        val query = "설현"
        val imageChunk = ImageChunk(isEnd = false, imageGroup = listOf())
        val mockImageResponse = flow {
            emit(imageChunk)
        }

        every {
            repository.searchImageBy(query = query)
        } returns mockImageResponse

        useCase.execute(query = query)
            .test {
                assertEquals(imageChunk, awaitItem())
                awaitComplete()
            }
    }
}
