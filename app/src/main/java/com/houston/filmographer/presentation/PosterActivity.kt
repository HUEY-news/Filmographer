package com.houston.filmographer.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ActivityPosterBinding

class PosterActivity : AppCompatActivity() {

    private var _binding: ActivityPosterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPosterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("POSTER")

        Glide.with(this)
            .load(image)
            .into(binding.imageView)
    }
}