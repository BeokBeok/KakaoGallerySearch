package com.beok.kakaogallerysearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.MainCoroutineRule
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.getOrAwaitValue
import com.beok.kakaogallerysearch.presentation.model.Gallery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
    fun `최초 검색 시 페이지 정보를 초기화합니다`() {
        viewModel.setupPageInfo(isNext = true)

        viewModel.setupPageInfo(isNext = false)

        assertTrue(viewModel.pageInfo.value == 1)
    }

    @Test
    fun `페이징 시 페이징 값이 더해집니다`() {
        viewModel.setupPageInfo(isNext = true)

        assertEquals(2, viewModel.pageInfo.value)
    }

    @Test
    fun `이미지와 비디오를 검색합니다`() = runBlocking {
        val (imageChunk, videoChunk) =
            ImageChunk(isEnd = false, imageGroup = listOf()) to
                VideoChunk(isEnd = false, videoGroup = listOf())
        val mockResponse = ImageVideoChunk(imageChunk = imageChunk, videoChunk = videoChunk)

        every {
            searchGalleryUseCase.execute(query = QUERY)
        } returns flow {
            emit(mockResponse)
        }

        viewModel.searchGallery(query = QUERY)
        val actual = imageChunk.imageGroup.map(Gallery::fromDomain)
            .plus(videoChunk.videoGroup.map(Gallery::fromDomain))
            .sortedByDescending(Gallery::datetime)

        assertEquals(viewModel.galleryGroup.getOrAwaitValue(), actual)
    }

    @Test
    fun `이미지를 클릭하면 해당 이미지를 보관합니다`() {
        val item = Gallery(datetime = 0, thumbnailUrl = "")

        viewModel.onClickForSave(item = item)

        assertEquals(item, viewModel.boxGroup.getOrAwaitValue().firstOrNull())
    }

    companion object {
        private const val QUERY = "설현"
    }
}
