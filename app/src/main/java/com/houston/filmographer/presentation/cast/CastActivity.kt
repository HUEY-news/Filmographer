package com.houston.filmographer.presentation.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.houston.filmographer.databinding.ActivityCastBinding

class CastActivity : AppCompatActivity() {

    private var _binding: ActivityCastBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TEST", "CAST ACTIVITY CREATED")
        _binding = ActivityCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO "Добавить вёрстку"
        // TODO "Прочитать идентификатор фильма из Intent"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TEST", "CAST ACTIVITY DESTROYED")
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