package com.houston.filmographer.presentation.movie.view_model

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.util.Creator

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    init { Log.v("TEST", "MOVIE PRESENTER CREATED") }

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "MOVIE PRESENTER CLEARED")
    }

    private val interactor = Creator.provideMovieInteractor(getApplication())
    private val key = "k_zcuw1ytf"
    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchMovie(key, currentQuery)
    }

    private val stateLiveData = MutableLiveData<MovieState>()
    fun observeState(): LiveData<MovieState> = stateLiveData
    private fun renderState(state: MovieState) { stateLiveData.postValue(state) }

    private val toastLiveData = MutableLiveData<ToastState>(ToastState.None)
    fun observeToast(): LiveData<ToastState> = toastLiveData
    private fun showToast(message: String) { toastLiveData.postValue(ToastState.Show(message)) }
    fun switchToastState() { toastLiveData.postValue(ToastState.None) }

    fun onDestroy() {
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
        Log.d("TEST", "SEND REQUEST")
        if (query.isNotEmpty()) {
            renderState(MovieState.Loading)
            interactor.searchMovie(key, query, object : MovieInteractor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    if (data != null) renderState(MovieState.Content(data))
                    if (message != null) {
                        renderState(MovieState.Error("Ошибка сети"))
                        showToast(message)
                    } else if (data?.isEmpty()!!) {
                        renderState(MovieState.Empty("Ничего не найдено"))
                    }
                }
            })
        } else {
            renderState(MovieState.Empty("Ничего не найдено"))
            showToast("Поле ввода пустое")
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}