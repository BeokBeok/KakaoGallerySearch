package com.beok.kakaogallerysearch.domain.repository

import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRepository {

    fun searchImageBy(query: String, page: Int = START_PAGE): Flow<ImageChunk>

    fun searchVideoBy(query: String, page: Int = START_PAGE): Flow<VideoChunk>

    companion object {
        private const val START_PAGE = 1
    }
}
