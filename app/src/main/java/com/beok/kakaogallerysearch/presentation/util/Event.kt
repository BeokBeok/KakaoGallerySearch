package com.beok.kakaogallerysearch.presentation.util

class Event<T>(private val content: T) {
    private var hasBeenHandled = false

    fun getContentIfNotHandled() = if (hasBeenHandled) {
        null
    } else {
        hasBeenHandled = true
        content
    }
}