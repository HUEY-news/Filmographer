package com.houston.filmographer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.houston.filmographer.data.creator.Creator
import com.houston.filmographer.databinding.ActivityPosterBinding

class PosterActivity : AppCompatActivity() {

    private val controller = Creator.providePosterController(this)

    private var _binding: ActivityPosterBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPosterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller.onCreate()

//        val image = intent.getStringExtra("POSTER")
//
//        Glide.with(this)
//            .load(image)
//            .into(binding.imageView)
    }
}