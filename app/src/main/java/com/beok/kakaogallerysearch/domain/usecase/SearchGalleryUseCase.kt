package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.GalleryChunk
import kotlinx.coroutines.flow.Flow

interface SearchGalleryUseCase {

    fun execute(query: String, page: Int = START_PAGE): Flow<GalleryChunk>

    companion object {
        private const val START_PAGE = 1
    }
}
