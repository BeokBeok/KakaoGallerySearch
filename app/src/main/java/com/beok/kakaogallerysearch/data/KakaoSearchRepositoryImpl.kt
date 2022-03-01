package com.beok.kakaogallerysearch.data

import com.beok.kakaogallerysearch.data.remote.KakaoSearchRemoteDataSource
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class KakaoSearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: KakaoSearchRemoteDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : KakaoSearchRepository {

    override fun searchImageBy(query: String, page: Int): Flow<ImageChunk> =
        remoteDataSource.searchImageBy(query, page)
            .map { it.toDomain() }
            .flowOn(dispatcher)

    override fun searchVideoBy(query: String, page: Int): Flow<VideoChunk> =
        remoteDataSource.searchVideoBy(query, page)
            .map { it.toDomain() }
            .flowOn(dispatcher)
}
