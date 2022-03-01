package com.beok.kakaogallerysearch.domain.usecase

import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.ImageVideoChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.zip

class SearchGalleryUseCaseImpl @Inject constructor(
    private val repository: KakaoSearchRepository
) : SearchGalleryUseCase {

    override fun execute(query: String, page: Int): Flow<ImageVideoChunk> =
        repository.searchImageBy(query, page)
            .catch { emit(ImageChunk(isEnd = true, imageGroup = listOf())) }
            .zip(
                repository.searchVideoBy(query, page)
                    .catch { emit(VideoChunk(isEnd = true, videoGroup = listOf())) }
            ) { imageChunk, videoChunk ->
                ImageVideoChunk(imageChunk, videoChunk)
            }
}
