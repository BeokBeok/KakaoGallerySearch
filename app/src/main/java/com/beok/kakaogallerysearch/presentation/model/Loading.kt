package com.beok.kakaogallerysearch.presentation.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class Loading(initialValue: Int = 0) {

    private val _isLoadingCount = MutableLiveData(initialValue)
    val isLoading: LiveData<Boolean> get() = Transformations.map(_isLoadingCount) { it > 0 }

    fun show() {
        _isLoadingCount.value = (_isLoadingCount.value ?: 0) + LOADING_UNIT
    }

    fun hide() {
        val count = (_isLoadingCount.value ?: 0) - LOADING_UNIT
        _isLoadingCount.value = if (count < 0) {
            0
        } else {
            count
        }
    }

    companion object {
        private const val LOADING_UNIT = 1
    }
}
