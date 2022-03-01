package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoResponse(

	@Json(name="documents")
	val documents: List<VideoItem>? = null,

	@Json(name="meta")
	val meta: Meta? = null
)

