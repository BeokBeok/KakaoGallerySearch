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

    // test 패턴이 어느정도 정해져 있는걸로 아는데요.
    // 제가 아는건 Given, When, Then 이고요.
    // 쓰신 mockk는 'Expected behavior and behavior verification'의 패턴을 지향 하는걸로 보여요. (제가 짧게 찾아봐서 확실치는 않습니다.)
    // 테스트를 짤때는 저런 패턴 테스트 명세를 작성하는게 좋아요.
    // 만약 Given, When, Then으로 명세를 작성한다면 저는 아래와 같이 작성할 것 같아요.

    // Given: 추가 로드 상황이고 페이징이 마지막 페이지일때 (추가 로드 | 페이징 마지막 O)
    // When: 이미지와 비디오 로드 시
    // Then: 기존 아이템에 로드된 아이템이 추가 되는지, pageInfo에 +1 되어 로드하는지, 로드 후 pageInfo에 마지막 페이지라고 표시 되는지

    // 이런식으로 정리하면 given이 아래와 같은 상황들이 존재 할 수 있을거에요.
    // 최초 로드 | 페이징 마지막 X
    // 최초 로드 | 페이징 마지막 O
    // 추가 로드 | 페이징 마지막 X
    // 추가 로드 | 페이징 마지막 O

    // 여기에 error 케이스만 추가해주면 searchGallery() 거의 커버리지 될것 같네요.
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

        viewModel.searchGallery(isNext = isNext, query = QUERY)
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
