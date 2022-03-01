package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchVideoUseCaseImpl @Inject constructor(
    private val repository: KakaoSearchRepository
) : SearchUseCase<VideoChunk> {

    override fun execute(query: String, page: Int): Flow<VideoChunk> =
        repository.searchVideoBy(query, page)
}
