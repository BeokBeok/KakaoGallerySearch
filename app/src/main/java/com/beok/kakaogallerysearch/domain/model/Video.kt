package com.beok.kakaogallerysearch.domain.model

import java.util.Date

data class Video(
    val title: String,
    val playTime: Int,
    val thumbnail: String,
    val url: String,
    val datetime: Date,
    val author: String
)
