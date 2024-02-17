package com.houston.filmographer.presentation.poster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.houston.filmographer.databinding.ActivityPosterBinding
import com.houston.filmographer.presentation.poster.PosterPresenter
import com.houston.filmographer.presentation.poster.PosterView
import com.houston.filmographer.util.Creator

class PosterActivity : AppCompatActivity(), PosterView {

    private lateinit var presenter: PosterPresenter

    private var _binding: ActivityPosterBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPosterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val image = intent.getStringExtra("POSTER") ?: ""
        presenter = Creator.providePosterPresenter(this, image)
        presenter.onCreate()
    }

    override fun setImage(image: String) {
        Glide.with(applicationContext)
            .load(image)
            .into(binding.imageViewCover)
    }
}