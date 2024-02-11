package com.houston.filmographer.presentation

import android.app.Activity
import com.bumptech.glide.Glide
import com.houston.filmographer.ui.PosterActivity

class PosterController(
    private val activity: Activity
) {

    fun onCreate() {
        val image = activity.intent.getStringExtra("POSTER")

        Glide.with(activity.applicationContext)
            .load(image)
            .into((activity as PosterActivity).binding.imageView)
    }
}