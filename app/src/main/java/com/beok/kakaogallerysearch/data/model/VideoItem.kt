package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.data.mapper.DataToDomainMapper
import com.beok.kakaogallerysearch.domain.model.Video
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
) : DataToDomainMapper<Video> {

	override fun toDomain(): Video = Video(
		title = title ?: "",
		playTime = playTime ?: 0,
		thumbnail = thumbnail ?: "",
		url = url ?: "",
		datetime = datetime ?: Date(0),
		author = author ?: ""
	)
}
