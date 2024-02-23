package com.houston.filmographer.presentation.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.houston.filmographer.R
import com.houston.filmographer.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO "Добавить первый фрагмент иерархии"
    }
}