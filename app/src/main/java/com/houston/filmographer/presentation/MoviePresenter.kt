package com.houston.filmographer.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.ui.MovieState
import com.houston.filmographer.util.Creator
import moxy.MvpPresenter

class MoviePresenter(
    private val context: Context,
    ): MvpPresenter<MovieView>() {

    init {
        Log.v("TEST", "MOVIE PRESENTER CREATED")
    }

    private val interactor = Creator.provideMovieInteractor(context)
    private val key = "k_zcuw1ytf"
    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchMovie(key, currentQuery)
    }

    fun render(state: MovieState) {
        viewState.render(state)
    }

    override fun onDestroy() {
        Log.v("TEST", "MOVIE PRESENTER DESTROYED")
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(text: String) {
        if (lastQuery == text) return
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
            render(MovieState.Loading)
            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    handler.post {
                        if (data != null) render(MovieState.Content(data))
                        if (message != null) {
                            render(MovieState.Error("Ошибка сети"))
                            viewState.showToast(message)
                        } else if (data?.isEmpty()!!) {
                            render(MovieState.Empty("Ничего не найдено"))
                        }
                    }
                }
            })
        } else {
            render(MovieState.Empty("Ничего не найдено"))
            viewState.showToast("Поле ввода пустое")
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}