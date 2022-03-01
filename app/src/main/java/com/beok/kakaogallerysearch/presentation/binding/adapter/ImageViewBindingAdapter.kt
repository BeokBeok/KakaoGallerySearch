package com.beok.kakaogallerysearch.presentation.binding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind_glide_imageUrl")
fun setGlideImageUrl(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .into(view)
}
