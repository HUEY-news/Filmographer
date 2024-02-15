package com.houston.filmographer.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.databinding.ActivityMovieBinding
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.presentation.MoviePresenter
import com.houston.filmographer.presentation.MovieView
import com.houston.filmographer.util.App
import com.houston.filmographer.util.Creator

class MovieActivity : AppCompatActivity(), MovieView {

    private var _binding: ActivityMovieBinding? = null
    private val binding get() = _binding!!

    private var presenter: MoviePresenter? = null
    private var watcher: TextWatcher? = null

    private val adapter = MovieAdapter { movie ->
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("POSTER", movie.image)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("TEST", "Активити пересоздана")
        presenter = (application as App).presenter

        if (presenter == null) {
            presenter = Creator.provideMoviePresenter(applicationContext)
            (application as App).presenter = presenter
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                presenter?.searchDebounce(text = text?.toString() ?: "")
            }
        }
        watcher?.let { watcher -> binding.editText.addTextChangedListener(watcher) }

        binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter?.sendRequest(text = binding.editText.text.toString())
            }
            false
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter?.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter?.detachView()
    }

    override fun onStop() {
        super.onStop()
        presenter?.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        presenter?.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        watcher?.let { watcher -> binding.editText.removeTextChangedListener(watcher) }
        presenter?.detachView()
        presenter?.onDestroy()
        if (isFinishing) (application as App).presenter = null
    }

    override fun render(state: MovieState) {
        when(state) {
            is MovieState.Loading -> showLoading()
            is MovieState.Content -> showContent(state.data)
            is MovieState.Error -> showError(state.message)
            is MovieState.Empty -> showEmpty(state.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        adapter.setContent(emptyList())
        binding.recyclerView.isVisible = false
        binding.textView.text = ""
        binding.textView.isVisible = false
    }

    private fun showContent(data: List<Movie>) {
        binding.progressBar.isVisible = false
        adapter.setContent(data)
        binding.recyclerView.isVisible = true
        binding.textView.text = ""
        binding.textView.isVisible = false
    }

    private fun showError(message: String) {
        binding.progressBar.isVisible = false
        adapter.setContent(emptyList())
        binding.recyclerView.isVisible = false
        binding.textView.text = message
        binding.textView.isVisible = true
    }

    private fun showEmpty(message: String) {
        showError(message)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}