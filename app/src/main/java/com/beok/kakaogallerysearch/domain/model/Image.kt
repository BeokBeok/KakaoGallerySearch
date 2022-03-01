package com.beok.kakaogallerysearch.domain.model

import java.util.Date

data class Image(
    val collection: String,
    val thumbnailUrl: String,
    val imageUrl: String,
    val width: Int,
    val height: Int,
    val displaySitename: String,
    val docUrl: String,
    val datetime: Date
)
