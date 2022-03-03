package com.beok.kakaogallerysearch.presentation.model

class PageInfo(
    value: Int = 1,
    isEnd: Boolean = false
) {
    private var _value = value
    val value get() = _value

    private var _isEnd = isEnd
    val isEnd get() = _isEnd

    fun setup(isNext: Boolean) {
        if (!isNext) {
            _value = 1
            _isEnd = false
        } else {
            _value += 1
        }
    }

    fun update(isEnd: Boolean) {
        _isEnd = isEnd
    }
}
