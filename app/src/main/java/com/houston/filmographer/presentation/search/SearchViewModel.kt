package com.houston.filmographer.presentation.search

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.Movie

class SearchViewModel(
    private val interactor: Interactor
) : ViewModel() {

    init { Log.v("TEST", "MOVIE VIE MODEL CREATED") }

    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchMovie(TV_API_KEY, currentQuery)
    }

    private val stateLiveData = MutableLiveData<SearchState>()
    private val mediatorStateLiveData = MediatorLiveData<SearchState>().also { liveData ->
        liveData.addSource(stateLiveData) { movieState ->
            liveData.value = when(movieState) {
                is SearchState.Content -> SearchState.Content(movieState.data.sortedByDescending { it.inFavorite })
                is SearchState.Empty -> movieState
                is SearchState.Error -> movieState
                is SearchState.Loading -> movieState
            }
        }
    }

    fun observeState(): LiveData<SearchState> = mediatorStateLiveData

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

    private fun renderState(state: SearchState) { stateLiveData.postValue(state) }

    private fun searchMovie(key: String, query: String) {
        Log.d("TEST", "SEND REQUEST")
        if (query.isNotEmpty()) {
            renderState(SearchState.Loading)
            interactor.searchMovie(key, query, object : Interactor.MovieConsumer {
                override fun consume(data: List<Movie>?, message: String?) {
                    if (data != null) renderState(SearchState.Content(data))
                    if (message != null) {
                        renderState(SearchState.Error("Ошибка сети"))
                        showToast(message)
                    } else if (data?.isEmpty()!!) {
                        renderState(SearchState.Empty("Ничего не найдено"))
                    }
                }
            })
        } else {
            renderState(SearchState.Empty("Ничего не найдено"))
            showToast("Поле ввода пустое")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "MOVIE VIEW MODEL CLEARED")
        handler.removeCallbacks(searchRunnable)
    }

    fun switchFavorite(movie: Movie) {
        if (movie.inFavorite) interactor.removeMovieFromFavorites(movie)
        else interactor.addMovieToFavorites(movie)
        updateContent(movie.id, movie.copy(inFavorite = !movie.inFavorite))
    }

    private fun updateContent(movieId: String, newMovie: Movie) {
        val currentState = stateLiveData.value
        if (currentState is SearchState.Content) {
            val movieIndex = currentState.data.indexOfFirst { it.id == movieId }
            if (movieIndex != -1) {
                stateLiveData.value = SearchState.Content(
                    currentState.data.toMutableList().also {
                        it[movieIndex] = newMovie
                    }
                )
            }
        }
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}