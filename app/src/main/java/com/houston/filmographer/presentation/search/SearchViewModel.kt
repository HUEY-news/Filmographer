package com.houston.filmographer.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.presentation.ToastState
import com.houston.filmographer.util.debounce
import kotlinx.coroutines.launch

class SearchViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var lastQuery: String? = null

    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = mediatorStateLiveData
    private val mediatorStateLiveData = MediatorLiveData<SearchState>().also { liveData ->
        liveData.addSource(stateLiveData) { searchState ->
            liveData.value = when(searchState) {
                is SearchState.Content -> SearchState.Content(searchState.data.sortedByDescending { it.inFavorite })
                is SearchState.Empty -> searchState
                is SearchState.Error -> searchState
                is SearchState.Loading -> searchState
            }
        }
    }

    private val toastLiveData = MutableLiveData<ToastState>(ToastState.None)
    fun observeToast(): LiveData<ToastState> = toastLiveData
    private fun showToast(message: String) { toastLiveData.postValue(ToastState.Show(message)) }
    fun switchToastState() { toastLiveData.postValue(ToastState.None) }

    val movieSearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { text ->
        searchMovie(TV_API_KEY, text)
    }

    fun searchDebounce(text: String) {
        if (lastQuery != text) {
            lastQuery = text
            val currentQuery = lastQuery ?: ""
            movieSearchDebounce(currentQuery)
        }
    }

    fun sendRequest(text: String) {
        lastQuery = text
        val currentQuery = lastQuery ?: ""
        searchMovie(TV_API_KEY, currentQuery)
    }

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private fun searchMovie(key: String, query: String) {
        if (query.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch {
                interactor
                    .searchMovie(key, query)
                    .collect { pair ->
                        if (pair.first != null) renderState(SearchState.Content(pair.first!!))
                        if (pair.second != null) {
                            renderState(SearchState.Error("Ошибка сети"))
                            showToast(pair.second!!)
                        } else if (pair.first?.isEmpty()!!) {
                            renderState(SearchState.Empty("Ничего не найдено"))
                        }
                    }
            }
        } else {
            renderState(SearchState.Empty("Ничего не найдено"))
            showToast("Поле ввода пустое")
        }
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