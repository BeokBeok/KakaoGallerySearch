package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class SearchGalleryUseCaseImpl @Inject constructor(
    private val repository: KakaoSearchRepository
) : SearchGalleryUseCase {

    // 코드 변경한건 제안정도입니다! 꼭 하실필요 없어요!
    override fun execute(
        query: String,
        page: Int
    ) = searchImageBy(query, page).zip(searchVideoBy(query, page)) { imageChunk, videoChunk ->
        ImageVideoChunk(imageChunk, videoChunk)
    }

    private fun searchImageBy(
        query: String,
        page: Int
    ) = repository.searchImageBy(query, page)
        .catch {
            emit(ImageChunk(isEnd = true, imageGroup = emptyList()))
        } // 가능한 부분인지 모르겠는데 emptyList()가 된다면 하는게 좋아보이네여
    // 그리고 error 발생시 빈 chunk를 보내는게 좋은건지도 한번 고민해두면 좋을것 같아요. 개선 방안이라도.
    // 어쩌면 에러를 presenter layer까지 보내고 거기서 리트라이 가능하도록 유독하는 방식이라던지.
    // 작업은... 하기에는 오래 걸리기 때문에 방안만 고민해두면 면접때 말은 할 수 있을겁니다.

    private fun searchVideoBy(
        query: String,
        page: Int
    ) = repository.searchVideoBy(query, page)
        .catch {
            emit(VideoChunk(isEnd = true, videoGroup = emptyList()))
        } // 가능한 부분인지 모르겠는데 emptyList()가 된다면 하는게 좋아보이네여
}
