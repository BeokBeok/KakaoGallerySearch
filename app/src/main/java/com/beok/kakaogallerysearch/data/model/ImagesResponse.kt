package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImagesResponse(

	@Json(name="documents")
	val documents: List<ImageItem>? = null,

	@Json(name="meta")
	val meta: Meta? = null
)
