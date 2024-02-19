package com.houston.filmographer.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.houston.filmographer.databinding.ActivityCastBinding

class CastActivity : AppCompatActivity() {

    private var _binding: ActivityCastBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO "Добавить вёрстку"
        // TODO "Прочитать идентификатор фильма из Intent"
    }

    companion object {
        private const val MOVIE_ID = "MOVIE_ID"

        fun newInstance(context: Context, movieId: String): Intent {
            val intent = Intent(context, CastActivity::class.java)
            intent.putExtra(MOVIE_ID, movieId)
            return intent
        }
    }
}