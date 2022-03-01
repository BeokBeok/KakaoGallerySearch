package com.beok.kakaogallerysearch.data.remote

import com.beok.kakaogallerysearch.data.model.ImageResponse
import com.beok.kakaogallerysearch.data.model.VideoResponse
import kotlinx.coroutines.flow.Flow

interface KakaoSearchRemoteDataSource {

    fun searchImageBy(query: String, page: Int = START_PAGE): Flow<ImageResponse>

    fun searchVideoBy(query: String, page: Int = START_PAGE): Flow<VideoResponse>

    companion object {
        private const val START_PAGE = 1
    }
}
