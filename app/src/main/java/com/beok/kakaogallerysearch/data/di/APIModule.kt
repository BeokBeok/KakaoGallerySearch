package com.beok.kakaogallerysearch.data.di

import com.beok.kakaogallerysearch.data.remote.KakaoSearchAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun providesKakaoSearchAPI(retrofit: Retrofit) =
        retrofit.create(KakaoSearchAPI::class.java)
}
