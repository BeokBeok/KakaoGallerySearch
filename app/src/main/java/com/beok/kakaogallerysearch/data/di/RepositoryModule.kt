package com.beok.kakaogallerysearch.data.di

import com.beok.kakaogallerysearch.data.KakaoSearchRepositoryImpl
import com.beok.kakaogallerysearch.domain.repository.KakaoSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsKakaoSearchRepository(impl: KakaoSearchRepositoryImpl): KakaoSearchRepository
}
