package com.beok.kakaogallerysearch.domain.model

data class ImageChunk(
    override val isEnd: Boolean,
    val imageGroup: List<Image>
) : Pageable
