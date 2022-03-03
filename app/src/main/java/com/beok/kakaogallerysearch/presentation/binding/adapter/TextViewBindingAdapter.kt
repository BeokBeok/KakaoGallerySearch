package com.beok.kakaogallerysearch.presentation.binding.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.beok.kakaogallerysearch.R
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("bind_setText_galleryDateTime")
fun setTextGalleryDate(view: TextView, datetime: Long) {
    val yyyyMMddFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
    val ahmmFormat = SimpleDateFormat("a h:mm", Locale.KOREA)
    view.text = view.context.getString(
        R.string.string_newline_string,
        yyyyMMddFormat.format(datetime),
        ahmmFormat.format(datetime)
    )
}
