package com.ets.inven.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

fun setPhoto(context: Context, photo: String, imageView: ImageView) {
    Glide.with(context)
        .load(GlobalData.STORAGE_PREFIX + photo)
        .centerCrop()
        .into(imageView)
}