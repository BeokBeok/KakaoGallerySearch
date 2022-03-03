package com.beok.kakaogallerysearch.presentation.model

data class ClickAction<T>(
    val bindingID: Int,
    val onClick: (item: T) -> Unit
)
