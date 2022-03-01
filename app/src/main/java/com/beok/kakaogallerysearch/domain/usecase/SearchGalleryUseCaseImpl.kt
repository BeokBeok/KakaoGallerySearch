package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.GalleryChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class SearchGalleryUseCaseImpl(
    private val repository: KakaoSearchRepository
) : SearchGalleryUseCase {

    override fun execute(query: String, page: Int): Flow<GalleryChunk> =
        repository.searchImageBy(query, page)
            .zip(repository.searchVideoBy(query, page)) { imageChunk, videoChunk ->
                GalleryChunk(image = imageChunk, video = videoChunk)
            }
}
