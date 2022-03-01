package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class VideoItem(

	@Json(name="title")
	val title: String? = null,

	@Json(name="play_time")
	val playTime: Int? = null,

	@Json(name="thumbnail")
	val thumbnail: String? = null,

	@Json(name="url")
	val url: String? = null,

	@Json(name="datetime")
	val datetime: Date? = null,

	@Json(name="author")
	val author: String? = null
)
