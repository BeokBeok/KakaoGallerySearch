package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class ImageItem(

	@Json(name="collection")
	val collection: String? = null,

	@Json(name="thumbnail_url")
	val thumbnailUrl: String? = null,

	@Json(name="image_url")
	val imageUrl: String? = null,

	@Json(name="width")
	val width: Int? = null,

	@Json(name="height")
	val height: Int? = null,

	@Json(name="display_sitename")
	val displaySitename: String? = null,

	@Json(name="doc_url")
	val docUrl: String? = null,

	@Json(name="datetime")
	val datetime: Date? = null
)
