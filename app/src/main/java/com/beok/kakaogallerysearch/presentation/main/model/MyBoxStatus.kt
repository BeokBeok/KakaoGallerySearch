package com.beok.kakaogallerysearch.presentation.main.model

sealed class MyBoxStatus {
    object Added : MyBoxStatus()
    object AlreadyAdded : MyBoxStatus()
}
