package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchImageUseCaseImpl @Inject constructor(
    private val repository: KakaoSearchRepository
) : SearchUseCase<ImageChunk> {

    override fun execute(query: String, page: Int): Flow<ImageChunk> =
        repository.searchImageBy(query, page)

}
