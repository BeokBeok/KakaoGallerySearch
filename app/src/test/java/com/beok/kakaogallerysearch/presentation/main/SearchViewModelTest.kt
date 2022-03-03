package com.beok.kakaogallerysearch.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.MainCoroutineRule
import com.beok.kakaogallerysearch.domain.model.Image
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
import com.beok.kakaogallerysearch.domain.model.Video
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.getOrAwaitValue
import com.beok.kakaogallerysearch.presentation.main.model.Gallery
import com.beok.kakaogallerysearch.presentation.main.model.MyBoxStatus
import io.mockk.every
import io.mockk.mockk
import java.util.Date
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    fun `이전에 검색한 데이터가 모두 마지막이였다면_다음 이미지와 비디오를 불러올 때_요청하지 않는다`() = runBlocking {
        // given
        val imageChunk = ImageChunk(
            isEnd = true,
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
            isEnd = true,
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
        every {
            searchGalleryUseCase.execute(query = QUERY)
        } returns flow {
            emit(ImageVideoChunk(imageChunk, videoChunk))
        }
        viewModel.searchGallery(isNext = false, query = QUERY)

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
    fun `보관함에 없는_이미지를 클릭하면_보관함에 추가합니다`() {
        // given
        val item = Gallery(datetime = 0, thumbnailUrl = "")

        // when
        viewModel.onClickForSave(item = item)

        // then
        assertEquals(item, viewModel.boxGroup.getOrAwaitValue().firstOrNull())
        assertEquals(
            MyBoxStatus.Added,
            viewModel.myBoxStatus.getOrAwaitValue().getContentIfNotHandled()
        )
    }

    @Test
    fun `보관함에 있는_이미지를 클릭하면_보관함에 추가하지 않습니다`() {
        // given
        val item = Gallery(datetime = 0, thumbnailUrl = "")
        viewModel.onClickForSave(item = item)

        // when
        viewModel.onClickForSave(item = item)

        // then
        assertEquals(1, viewModel.boxGroup.getOrAwaitValue().size)
        assertEquals(
            MyBoxStatus.AlreadyAdded,
            viewModel.myBoxStatus.getOrAwaitValue().getContentIfNotHandled()
        )
    }

    companion object {
        private const val QUERY = "설현"
    }
}
