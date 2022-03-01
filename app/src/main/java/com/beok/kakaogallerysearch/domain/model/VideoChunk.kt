package com.beok.kakaogallerysearch.domain.model

data class VideoChunk(
    override val isEnd: Boolean,
    val videoGroup: List<Video>
) : Pageable
