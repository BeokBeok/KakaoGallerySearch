package com.beok.kakaogallerysearch.data.di

import com.beok.kakaogallerysearch.data.remote.KakaoSearchRemoteDataSource
import com.beok.kakaogallerysearch.data.remote.KakaoSearchRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsKakaoSearchRemoteDataSource(
        impl: KakaoSearchRemoteDataSourceImpl
    ): KakaoSearchRemoteDataSource
}
