package com.houston.filmographer.presentation.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.ActivityCastBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CastActivity : AppCompatActivity() {

    private var _binding: ActivityCastBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CastViewModel> {
        parametersOf(intent.getStringExtra(MOVIE_ID))
    }

    private val adapter = CastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("TEST", "CAST ACTIVITY CREATED")
        _binding = ActivityCastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.observeState().observe(this) { state -> render(state) }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TEST", "CAST ACTIVITY DESTROYED")
    }

    private fun render(state: CastState) {
        when (state) {
            is CastState.Loading -> showLoading()
            is CastState.Content -> showContent(state)
            is CastState.Error -> showError(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter.setData(emptyList())
        binding.linearLayout.isVisible = false
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showContent(state: CastState.Content) {
        binding.progressBar.isVisible = false
        binding.textViewMovieTitle.text = state.data.fullTitle
        adapter.setData(state.data.directors + state.data.writers + state.data.actors + state.data.others)
        binding.linearLayout.isVisible = true
        binding.textViewErrorMessage.text = ""
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        adapter.setData(emptyList())
        binding.linearLayout.isVisible = false
        binding.textViewErrorMessage.text = message
        binding.textViewErrorMessage.isVisible = true
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