package com.houston.filmographer.presentation

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.houston.filmographer.data.creator.Creator
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.ui.MovieActivity
import com.houston.filmographer.ui.MovieAdapter

class MovieController(
    private val activity: Activity,
    private val adapter: MovieAdapter
    ) {

    private val interactor = Creator.provideMovieInteractor(activity)
    private val key = "k_zcuw1ytf"
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable {
        searchMovie(key, (activity as MovieActivity).binding.editText.text.toString())
    }

    fun onCreate() {
        (activity as MovieActivity).binding.recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        activity.binding.recyclerView.adapter = adapter

        activity.binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounce()
            }
        })

        activity.binding.editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                handler.removeCallbacks(searchRunnable)
                handler.post(searchRunnable)
            }
            false
        }
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchMovie(key: String, query: String) {
        Log.v("TEST", "Зпрос отправлен!")
        if (query.isNotEmpty()) {

            (activity as MovieActivity).binding.progressBar.isVisible = true
            adapter.setContent(emptyList())
            showMessage("", "")

            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    handler.post {
                        (activity).binding.progressBar.isVisible = false
                        if (data != null) adapter.setContent(data)
                        if (message != null) showMessage("Ошибка сети", message)
                        else if (data?.isEmpty()!!) showMessage("Ничего не найдено", "")
                        else showMessage("", "")

                    }
                }
            })
        } else showMessage("Ничего не найдено", "Поле ввода пустое")
    }

    private fun showMessage(mainMessage: String, additionalMessage: String) {
        if (mainMessage.isNotEmpty()) {
            (activity as MovieActivity).binding.textView.isVisible = true
            adapter.setContent(emptyList())
            (activity as MovieActivity).binding.textView.text = mainMessage
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(
                    activity,
                    additionalMessage,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        } else (activity as MovieActivity).binding.textView.isVisible = false
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

}