package com.beok.kakaogallerysearch.domain.di

import com.beok.kakaogallerysearch.domain.model.ImageChunk
import com.beok.kakaogallerysearch.domain.model.VideoChunk
import com.beok.kakaogallerysearch.domain.usecase.SearchImageUseCaseImpl
import com.beok.kakaogallerysearch.domain.usecase.SearchUseCase
import com.beok.kakaogallerysearch.domain.usecase.SearchVideoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun bindsSearchImageUseCase(impl: SearchImageUseCaseImpl): SearchUseCase<ImageChunk>

    @Binds
    @Singleton
    fun bindsSearchVideoUseCase(impl: SearchVideoUseCaseImpl): SearchUseCase<VideoChunk>
}
