package com.beok.kakaogallerysearch.presentation.main.model

import com.beok.kakaogallerysearch.domain.model.Image
import com.beok.kakaogallerysearch.domain.model.Video

data class Gallery(
    val datetime: Long,
    val thumbnailUrl: String,
) {

    companion object {

        fun fromDomain(image: Image) = Gallery(
            datetime = image.datetime.time,
            thumbnailUrl = image.thumbnailUrl,
        )

        fun fromDomain(video: Video) = Gallery(
            datetime =  video.datetime.time,
            thumbnailUrl = video.thumbnail
        )
    }
}
