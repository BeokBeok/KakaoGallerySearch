package com.beok.kakaogallerysearch.domain.usecase

import app.cash.turbine.test
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
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
    fun `이미지와 동영상을 검색합니다`() = runBlocking {
        // given
        val (imageChunk, videoChunk) =
            ImageChunk(isEnd = false, imageGroup = emptyList()) to
                VideoChunk(isEnd = false, videoGroup = emptyList())
        every {
            repository.searchImageBy(query = QUERY)
        } returns flow {
            emit(imageChunk)
        }
        every {
            repository.searchVideoBy(query = QUERY)
        } returns flow {
            emit(videoChunk)
        }

        // when
        useCase.execute(query = QUERY)
            .test {
                // then
                assertEquals(ImageVideoChunk(imageChunk, videoChunk), awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `이미지 검색시 에러가 발생하고 동영상 검색시 데이터를 내려줄 경우_동영상 검색 데이터만 전달합니다`() = runBlocking {
        // given
        val videoChunk = VideoChunk(isEnd = false, videoGroup = emptyList())
        every {
            repository.searchImageBy(query = QUERY)
        } returns flow {
            throw Throwable("HTTP 400 Bad Request")
        }
        every {
            repository.searchVideoBy(query = QUERY)
        } returns flow {
            emit(videoChunk)
        }

        // when
        useCase.execute(query = QUERY)
            .test {
                // then
                assertEquals(
                    ImageVideoChunk(
                        ImageChunk(isEnd = true, imageGroup = emptyList()),
                        videoChunk
                    ),
                    awaitItem()
                )
                awaitComplete()
            }
    }

    @Test
    fun `이미지 검색시 데이터를 내려주고 동영상 검색시 에러가 발생할 경우_이미지 검색 데이터만 전달합니다`() = runBlocking {
        // given
        val imageChunk = ImageChunk(isEnd = false, imageGroup = emptyList())
        every {
            repository.searchImageBy(query = QUERY)
        } returns flow {
            emit(imageChunk)
        }
        every {
            repository.searchVideoBy(query = QUERY)
        } returns flow {
            throw Throwable("HTTP 400 Bad Request")
        }

        // when
        useCase.execute(query = QUERY)
            .test {
                // then
                assertEquals(
                    ImageVideoChunk(
                        imageChunk,
                        VideoChunk(isEnd = true, videoGroup = emptyList())
                    ),
                    awaitItem()
                )
                awaitComplete()
            }
    }

    @Test
    fun `이미지 및 동영상 검색시 에러가 발생하는 경우_빈 데이터를 전달합니다`() = runBlocking {
        // given
        every {
            repository.searchImageBy(query = QUERY)
        } returns flow {
            throw Throwable("HTTP 400 Bad Request")
        }
        every {
            repository.searchVideoBy(query = QUERY)
        } returns flow {
            throw Throwable("HTTP 400 Bad Request")
        }

        // when
        useCase.execute(query = QUERY)
            .test {
                // then
                assertEquals(
                    ImageVideoChunk(
                        ImageChunk(isEnd = true, imageGroup = emptyList()),
                        VideoChunk(isEnd = true, videoGroup = emptyList())
                    ),
                    awaitItem()
                )
                awaitComplete()
            }
    }

    companion object {
        private const val QUERY = "설현"
    }
}
