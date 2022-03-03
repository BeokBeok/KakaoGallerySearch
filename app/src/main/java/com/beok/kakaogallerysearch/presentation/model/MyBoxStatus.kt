package com.beok.kakaogallerysearch.presentation.model

sealed class MyBoxStatus {
    object Added : MyBoxStatus()
    object AlreadyAdded : MyBoxStatus()
}
