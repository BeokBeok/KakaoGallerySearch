package com.beok.kakaogallerysearch.data.remote

import com.beok.kakaogallerysearch.data.model.ImageResponse
import com.beok.kakaogallerysearch.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoSearchAPI {

    @GET("/v2/search/image")
    suspend fun searchImageBy(
        @Query("query") query: String,
        @Query("page") page: Int = START_PAGE,
        @Query("size") size: Int = PER_PAGE
    ): ImageResponse

    @GET("/v2/search/vclip")
    suspend fun searchVideoBy(
        @Query("query") query: String,
        @Query("page") page: Int = START_PAGE,
        @Query("size") size: Int = PER_PAGE
    ): VideoResponse

    companion object {
        private const val START_PAGE = 1
        private const val PER_PAGE = 15
    }
}
