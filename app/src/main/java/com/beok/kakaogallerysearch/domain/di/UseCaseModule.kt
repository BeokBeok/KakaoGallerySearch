package com.beok.kakaogallerysearch.domain.di

import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCase
import com.beok.kakaogallerysearch.domain.usecase.SearchGalleryUseCaseImpl
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
    fun bindsSearchGalleryUseCase(impl: SearchGalleryUseCaseImpl): SearchGalleryUseCase
}
