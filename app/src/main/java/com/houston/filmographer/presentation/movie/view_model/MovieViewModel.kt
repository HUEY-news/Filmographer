package com.houston.filmographer.presentation.movie.view_model

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.model.Movie

class MovieViewModel(
    private val interactor: MovieInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    init { Log.v("TEST", "MOVIE PRESENTER CREATED") }

    private val key = "k_zcuw1ytf"
    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchMovie(key, currentQuery)
    }

    private val stateLiveData = MutableLiveData<MovieState>()
    private val mediatorStateLiveData = MediatorLiveData<MovieState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when(movieState) {
                is MovieState.Content -> MovieState.Content(movieState.data.sortedByDescending { it.inFavorite })
                is MovieState.Empty -> movieState
                is MovieState.Error -> movieState
                is MovieState.Loading -> movieState
            }
        }
    }

    fun observeState(): LiveData<MovieState> = mediatorStateLiveData

    private val toastLiveData = MutableLiveData<ToastState>(ToastState.None)
    fun observeToast(): LiveData<ToastState> = toastLiveData
    private fun showToast(message: String) { toastLiveData.postValue(ToastState.Show(message)) }
    fun switchToastState() { toastLiveData.postValue(ToastState.None) }

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

    private fun renderState(state: MovieState) { stateLiveData.postValue(state) }

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

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "MOVIE PRESENTER CLEARED")
        handler.removeCallbacks(searchRunnable)
    }

    fun switchFavorite(movie: Movie) {
        if (movie.inFavorite) interactor.removeMovieFromFavorites(movie)
        else interactor.addMovieToFavorites(movie)
        updateContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value
        if (currentState is MovieState.Content) {
            val movieIndex = currentState.data.indexOfFirst { it.id == movieId }
            if (movieIndex != -1) {
                stateLiveData.value = MovieState.Content(
                    currentState.data.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }
}