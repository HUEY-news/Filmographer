package com.houston.filmographer.presentation.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.domain.model.MovieCast
import kotlinx.coroutines.launch

class MovieCastViewModel(
    private val movieId: String,
    private val interactor: MovieInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MovieCastState>()
    fun observeState(): LiveData<MovieCastState> = stateLiveData

    init {
        stateLiveData.postValue(MovieCastState.Loading)
        viewModelScope.launch {
            interactor
                .getMovieCast(TV_API_KEY, movieId)
                .collect { pair ->
                    if (pair.first != null) stateLiveData.postValue(dataToCastState(pair.first!!))
                    else stateLiveData.postValue(MovieCastState.Error(pair.second ?: "Неизвестная ошибка"))
                }
        }
    }

    private fun dataToCastState(data: MovieCast): MovieCastState {
        val items = buildList<MovieCastItem> {
            if (data.directors.isNotEmpty()) {
                this += MovieCastItem.HeaderItem("Режиссёры")
                this += data.directors.map { person -> MovieCastItem.PersonItem(person) }
            }
            if (data.writers.isNotEmpty()) {
                this += MovieCastItem.HeaderItem("Сценаристы")
                this += data.writers.map { person -> MovieCastItem.PersonItem(person) }
            }
            if (data.actors.isNotEmpty()) {
                this += MovieCastItem.HeaderItem("Актёры")
                this += data.actors.map { person -> MovieCastItem.PersonItem(person) }
            }
            if (data.others.isNotEmpty()) {
                this += MovieCastItem.HeaderItem("Другие")
                this += data.others.map { person -> MovieCastItem.PersonItem(person) }
            }
        }

        return MovieCastState.Content(
            title = data.fullTitle,
            items = items
        )
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
    }
}