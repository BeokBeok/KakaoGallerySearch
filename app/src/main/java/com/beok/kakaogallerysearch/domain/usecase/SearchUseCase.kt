package com.beok.kakaogallerysearch.domain.usecase

import kotlinx.coroutines.flow.Flow

interface SearchUseCase<out T> {

    fun execute(query: String, page: Int = START_PAGE): Flow<T>

    companion object {
        private const val START_PAGE = 1
    }
}
