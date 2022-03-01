package com.beok.kakaogallerysearch

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object ISO8601Utils {

    fun fromString(date: String): Long = OffsetDateTime
        .parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        .toInstant()
        .toEpochMilli()
}