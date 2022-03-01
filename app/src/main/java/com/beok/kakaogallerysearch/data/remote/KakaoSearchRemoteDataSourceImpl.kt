package com.beok.kakaogallerysearch.data.remote

import com.beok.kakaogallerysearch.data.model.ImageResponse
import com.beok.kakaogallerysearch.data.model.VideoResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class KakaoSearchRemoteDataSourceImpl @Inject constructor(
    private val api: KakaoSearchAPI
) : KakaoSearchRemoteDataSource {

    override fun searchImageBy(query: String, page: Int): Flow<ImageResponse> = flow {
        emit(api.searchImageBy(query, page))
    }

    override fun searchVideoBy(query: String, page: Int): Flow<VideoResponse> = flow {
        emit(api.searchVideoBy(query, page))
    }
}
