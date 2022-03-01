package com.beok.kakaogallerysearch.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beok.kakaogallerysearch.MainCoroutineRule
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchUseCase
import com.beok.kakaogallerysearch.getOrAwaitValue
import com.beok.kakaogallerysearch.presentation.model.Gallery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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

    private val searchImageUseCase: SearchUseCase<ImageChunk> = mockk(relaxed = true)
    private val searchVideoUseCase: SearchUseCase<VideoChunk> = mockk(relaxed = true)
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setup() {
        viewModel = SearchViewModel(
            searchImageUseCase = searchImageUseCase,
            searchVideoUseCase = searchVideoUseCase
        )
    }

    @Test
    fun `최초 검색 시 페이지 정보를 초기화합니다`() {
        viewModel.setupPageInfo(isNext = true)

        viewModel.setupPageInfo(isNext = false)

        assertTrue(viewModel.imagePageInfo.value == 1)
        assertTrue(viewModel.videoPageInfo.value == 1)
    }

    @Test
    fun `페이징 시 페이징 값이 더해집니다`() {
        viewModel.setupPageInfo(isNext = true)

        assertEquals(2, viewModel.imagePageInfo.value)
        assertEquals(2, viewModel.videoPageInfo.value)
    }

    @Test
    fun `비디오를 검색합니다`() = runBlocking {
        val mockResponse = VideoChunk(isEnd = false, videoGroup = listOf())

        every {
            searchVideoUseCase.execute(query = QUERY)
        } returns flow {
            emit(mockResponse)
        }

        viewModel.searchByVideo(query = QUERY)
        val actual = mockResponse.videoGroup.map(Gallery::fromDomain)

        assertEquals(viewModel.galleryGroup.getOrAwaitValue(), actual)
    }

    @Test
    fun `이미지를 검색합니다`() = runBlocking {
        val mockResponse = ImageChunk(isEnd = false, imageGroup = listOf())

        every {
            searchImageUseCase.execute(query = QUERY)
        } returns flow {
            emit(mockResponse)
        }

        viewModel.searchByImage(query = QUERY)
        val actual = mockResponse.imageGroup.map(Gallery::fromDomain)

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
