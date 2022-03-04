package com.beok.kakaogallerysearch.presentation.binding.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.beok.kakaogallerysearch.R
import com.bumptech.glide.Glide

@BindingAdapter("bind_glide_imageUrl")
fun setGlideImageUrl(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .placeholder(R.mipmap.ic_launcher_round)
        .into(view)
}
