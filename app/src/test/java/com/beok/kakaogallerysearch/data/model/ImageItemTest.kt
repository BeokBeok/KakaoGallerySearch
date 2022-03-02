package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.ISO8601Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

// api response가 잘 변환 되는지 보는 테스트가 어떤 의미가 있는지 정리해둘 필요가 있어보여요.
// 개인적으로 저는 좋아하진 않는게, moshi가 잘 동작하는지 테스트하는 것처럼 보여서요.
// 그런 테스트는 moshi 라이브러리 내에서 해야한다고 생각하는 주의라 여튼 이런 질문이 나올 수 있으니 충분히 리저너블한 답변을 준비해보시는게 좋아보입니다!
class ImageItemTest {

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    @Test
    fun `Json을 Response로 변환합니다`() {
        val actual = moshi.adapter(ImageItem::class.java)
            .fromJson(IMAGE_ITEM_JSON)

        assertEquals(COLLECTION, actual?.collection)
        assertEquals(THUMBNAIL_URL, actual?.thumbnailUrl)
        assertEquals(IMAGE_URL, actual?.imageUrl)
        assertEquals(WIDTH, actual?.width)
        assertEquals(HEIGHT, actual?.height)
        assertEquals(DISPLAY_SITENAME, actual?.displaySitename)
        assertEquals(DOC_URL, actual?.docUrl)
        assertEquals(ISO8601Utils.fromString(DATETIME), actual?.datetime?.time)
    }

    @Test
    fun `domain 모델로 변환합니다`() {
        val response = ImageItem(
            collection = COLLECTION,
            thumbnailUrl = THUMBNAIL_URL,
            imageUrl = IMAGE_URL,
            width = WIDTH,
            height = HEIGHT,
            displaySitename = DISPLAY_SITENAME,
            docUrl = DOC_URL,
            datetime = Date(ISO8601Utils.fromString(DATETIME))
        )
        val actual = response.toDomain()

        actual.run {
            assertEquals(COLLECTION, collection)
            assertEquals(THUMBNAIL_URL, thumbnailUrl)
            assertEquals(IMAGE_URL, imageUrl)
            assertEquals(WIDTH, width)
            assertEquals(HEIGHT, height)
            assertEquals(DISPLAY_SITENAME, displaySitename)
            assertEquals(DOC_URL, docUrl)
            assertEquals(ISO8601Utils.fromString(DATETIME), datetime.time)
        }
    }

    companion object {
        private const val COLLECTION = "news"
        private const val THUMBNAIL_URL = "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp"
        private const val IMAGE_URL = "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg"
        private const val WIDTH = 540
        private const val HEIGHT = 457
        private const val DISPLAY_SITENAME = "한국경제TV"
        private const val DOC_URL = "http://v.media.daum.net/v/20170621155930002"
        private const val DATETIME = "2017-06-21T15:59:30.000+09:00"
        private val IMAGE_ITEM_JSON =
            """
                {
                    "collection": "$COLLECTION",
                    "thumbnail_url": "$THUMBNAIL_URL",
                    "image_url": "$IMAGE_URL",
                    "width": "$WIDTH",
                    "height": "$HEIGHT",
                    "display_sitename": "$DISPLAY_SITENAME",
                    "doc_url": "$DOC_URL",
                    "datetime": "$DATETIME"
                }
            """.trimIndent()
    }
}
