package com.beok.kakaogallerysearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.MainCoroutineRule
import com.beok.kakaogallerysearch.domain.model.Image
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
import com.beok.kakaogallerysearch.domain.model.Video
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.getOrAwaitValue
import com.beok.kakaogallerysearch.presentation.model.Gallery
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val searchGalleryUseCase: SearchGalleryUseCase = mockk(relaxed = true)
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(searchGalleryUseCase = searchGalleryUseCase)
    }

    @Test
    fun `이미지와 비디오를 검색합니다`() = runBlocking {
        // given
        val (imageChunk, videoChunk) =
            ImageChunk(isEnd = false, imageGroup = emptyList()) to
                    VideoChunk(isEnd = false, videoGroup = emptyList())
        val mockResponse = ImageVideoChunk(imageChunk = imageChunk, videoChunk = videoChunk)
        val expected = imageChunk.imageGroup.map(Gallery::fromDomain)
            .plus(videoChunk.videoGroup.map(Gallery::fromDomain))
            .sortedByDescending(Gallery::datetime)
        every {
            searchGalleryUseCase.execute(query = QUERY)
        } returns flow {
            emit(mockResponse)
        }

        // when
        viewModel.searchGallery(isNext = false, query = QUERY)

        // then
        assertEquals(expected, viewModel.galleryGroup.getOrAwaitValue())
    }

    @Test
    fun `검색한 이미지와 비디오가 있는 상태에서_다음 이미지와 비디오를 불러옵니다`() = runBlocking {
        // given
        val imageChunk = ImageChunk(
            isEnd = false,
            imageGroup = listOf(
                Image(
                    collection = "",
                    thumbnailUrl = "",
                    imageUrl = "",
                    width = 0,
                    height = 0,
                    displaySitename = "",
                    docUrl = "",
                    datetime = Date(0)
                )
            )
        )
        val videoChunk = VideoChunk(
            isEnd = false,
            videoGroup = listOf(
                Video(
                    title = "",
                    playTime = 0,
                    thumbnail = "",
                    url = "",
                    datetime = Date(0),
                    author = ""
                )
            )
        )
        val mockResponse = ImageVideoChunk(imageChunk = imageChunk, videoChunk = videoChunk)
        every {
            searchGalleryUseCase.execute(query = QUERY, page = 2)
        } returns flow {
            emit(mockResponse)
        }

        // when
        viewModel.searchGallery(isNext = true, query = QUERY)

        // then
        assertEquals(2, viewModel.galleryGroup.getOrAwaitValue().size)
    }

    @Test
    fun `이미지와 비디오 검색할 때_에러가 발생하면_error값을 설정합니다`() = runBlocking {
        // given
        val mockResponse = Throwable("HTTP 400 Bad Request")
        every {
            searchGalleryUseCase.execute(query = QUERY)
        } returns flow {
            throw mockResponse
        }

        // when
        viewModel.searchGallery(isNext = false, query = QUERY)

        // then
        assertEquals(mockResponse, viewModel.error.getOrAwaitValue().getContentIfNotHandled())
    }

    @Test
    fun `이미지를 클릭하면_클릭한 이미지를 보관합니다`() {
        // given
        val item = Gallery(datetime = 0, thumbnailUrl = "")

        // when
        viewModel.onClickForSave(item = item)

        // then
        assertEquals(item, viewModel.boxGroup.getOrAwaitValue().firstOrNull())
    }

    companion object {
        private const val QUERY = "설현"
    }
}
