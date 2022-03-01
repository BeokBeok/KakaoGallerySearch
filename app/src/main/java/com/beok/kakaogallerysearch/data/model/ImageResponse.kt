package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.data.mapper.DataToDomainMapper
import com.beok.kakaogallerysearch.data.mapper.toDomain
import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse(

	@Json(name="documents")
	val documents: List<ImageItem>? = null,

	@Json(name="meta")
	val meta: Meta? = null
) : DataToDomainMapper<ImageChunk> {

	override fun toDomain(): ImageChunk = ImageChunk(
		isEnd = meta?.isEnd ?: false,
		imageGroup = documents?.toDomain() ?: emptyList()
	)
}
