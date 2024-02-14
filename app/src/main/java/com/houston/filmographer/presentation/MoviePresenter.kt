package com.houston.filmographer.presentation

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.houston.filmographer.data.creator.Creator
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieInteractor

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

            view.showProgressBar(true)
            view.setContent(emptyList())
            showMessage("", "")

            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    handler.post {
                        view.showProgressBar(false)
                        if (data != null) view.setContent(data)
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
            view.showMainMessage(true)
            view.setContent(emptyList())
            view.setMainMessage(mainMessage)
            if (additionalMessage.isNotEmpty()) {
                view.showAdditionalMessage(additionalMessage)
            }
        } else view.showMainMessage(false)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}