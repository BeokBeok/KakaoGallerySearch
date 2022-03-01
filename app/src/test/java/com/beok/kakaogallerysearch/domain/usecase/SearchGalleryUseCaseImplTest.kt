package com.beok.kakaogallerysearch.domain.usecase

import app.cash.turbine.test
import com.beok.kakaogallerysearch.domain.model.GalleryChunk
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

class SearchGalleryUseCaseImplTest {

    private val repository: KakaoSearchRepository = mockk(relaxed = true)
    private lateinit var useCase: SearchGalleryUseCase

    @Before
    fun setup() {
        useCase = SearchGalleryUseCaseImpl(repository = repository)
    }

    @Test
    fun `이미지와 비디오를 검색합니다`() = runBlocking {
        val query = "설현"
        val (imageChunk, videoChunk) =
            ImageChunk(isEnd = false, imageGroup = listOf()) to
                    VideoChunk(isEnd = false, videoGroup = listOf())
        val mockImageResponse = flow {
            emit(imageChunk)
        }
        val mockVideoResponse = flow {
            emit(videoChunk)
        }

        every {
            repository.searchImageBy(query = query)
        } returns mockImageResponse
        every {
            repository.searchVideoBy(query = query)
        } returns mockVideoResponse

        useCase.execute(query = query)
            .test {
                assertEquals(GalleryChunk(image = imageChunk, video = videoChunk), awaitItem())
                awaitComplete()
            }
    }
}
