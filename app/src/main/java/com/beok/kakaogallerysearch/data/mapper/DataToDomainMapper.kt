package com.beok.kakaogallerysearch.data.mapper

interface DataToDomainMapper<T> {

    fun toDomain(): T
}

fun <T> List<DataToDomainMapper<T>>.toDomain(): List<T> = map { it.toDomain() }
