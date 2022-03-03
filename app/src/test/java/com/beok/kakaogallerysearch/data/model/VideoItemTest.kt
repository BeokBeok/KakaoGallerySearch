package com.beok.kakaogallerysearch.data.model

import com.beok.kakaogallerysearch.ISO8601Utils
import java.util.Date
import org.junit.Assert.assertEquals
import org.junit.Test

class VideoItemTest {

    @Test
    fun `Response를_domain 모델로 변환합니다`() {
        // given
        val response = VideoItem(
            title = TITLE,
            playTime = PLAY_TIME,
            thumbnail = THUMBNAIL,
            url = URL,
            datetime = Date(ISO8601Utils.fromString(DATETIME)),
            author = AUTHOR
        )

        // when
        val actual = response.toDomain()

        // then
        actual.run {
            assertEquals(TITLE, title)
            assertEquals(PLAY_TIME, playTime)
            assertEquals(THUMBNAIL, thumbnail)
            assertEquals(URL, url)
            assertEquals(ISO8601Utils.fromString(DATETIME), datetime.time)
            assertEquals(AUTHOR, author)
        }
    }

    companion object {
        private const val TITLE = "AOA 지민·김용만, 돼지꼬리 맛에 정신혼미 ‘극찬세례’"
        private const val PLAY_TIME = 185
        private const val THUMBNAIL = "https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F"
        private const val URL = "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634"
        private const val DATETIME = "2017-05-06T00:36:45+09:00"
        private const val AUTHOR = "_SBS"
    }
}
