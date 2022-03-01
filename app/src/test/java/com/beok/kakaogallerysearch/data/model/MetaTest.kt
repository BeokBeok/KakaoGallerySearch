package com.beok.kakaogallerysearch.data.model

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MetaTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Test
    fun `Json을 Response로 변환합니다`() {
        val actual = moshi.adapter(Meta::class.java)
            .fromJson(META_JSON)

        assertEquals(TOTAL_COUNT, actual?.totalCount)
        assertEquals(PAGEABLE_COUNT, actual?.pageableCount)
        assertEquals(IS_END, actual?.isEnd)
    }

    companion object {
        private const val TOTAL_COUNT = 422583
        private const val PAGEABLE_COUNT = 3854
        private const val IS_END = false
        private val META_JSON =
            """
                {
                    "total_count": $TOTAL_COUNT,
                    "pageable_count": $PAGEABLE_COUNT,
                    "is_end": $IS_END
                }
            """.trimIndent()
    }
}