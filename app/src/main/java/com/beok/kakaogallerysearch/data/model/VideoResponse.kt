package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.data.mapper.DataToDomainMapper
import com.beok.kakaogallerysearch.data.mapper.toDomain
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoResponse(

	@Json(name="documents")
	val documents: List<VideoItem>? = null,

	@Json(name="meta")
	val meta: Meta? = null
) : DataToDomainMapper<VideoChunk> {

	override fun toDomain(): VideoChunk = VideoChunk(
		isEnd = meta?.isEnd ?: false,
		videoGroup = documents?.toDomain() ?: emptyList()
	)
}

