package com.houston.filmographer.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.ui.MovieState
import com.houston.filmographer.util.Creator

class MoviePresenter(
    private val view: MovieView,
    private val context: Context,
    ) {

    private val interactor = Creator.provideMovieInteractor(context)
    private val key = "k_zcuw1ytf"
    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchMovie(key, currentQuery)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(text: String) {
        lastQuery = text
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun sendRequest(text: String) {
        lastQuery = text
        handler.removeCallbacks(searchRunnable)
        handler.post(searchRunnable)
    }

    private fun searchMovie(key: String, query: String) {
        Log.v("TEST", "Зпрос отправлен!")
        if (query.isNotEmpty()) {
            view.render(MovieState(emptyList(), true, null))
            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    handler.post {
                        if (data != null) view.render(MovieState(data, false, null))
                        if (message != null) {
                            view.render(MovieState(emptyList(), false, "Ошибка сети"))
                            view.showToast(message)
                        } else if (data?.isEmpty()!!) {
                            view.render(MovieState(emptyList(), false, "Ничего не найдено"))
                        }
                    }
                }
            })
        } else {
            view.render(MovieState(emptyList(), false, "Ничего не найдено"))
            view.showToast("Поле ввода пустое")
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}