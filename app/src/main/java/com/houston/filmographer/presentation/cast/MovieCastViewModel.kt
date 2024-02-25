package com.houston.filmographer.presentation.cast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.MovieCast

class MovieCastViewModel(
    private val movieId: String,
    private val interactor: Interactor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<MovieCastState>()
    fun observeState(): LiveData<MovieCastState> = stateLiveData

    init {
        Log.v("TEST", "CAST VIEW MODEL CREATED")
        stateLiveData.postValue(MovieCastState.Loading)
        interactor.getMovieCast(TV_API_KEY, movieId, object : Interactor.MovieCastConsumer {
            override fun consume(data: MovieCast?, message: String?) {
                if (data != null) stateLiveData.postValue(dataToCastState(data))
                else stateLiveData.postValue(MovieCastState.Error(message ?: "Неизвестная ошибка"))
            }
        })
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

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "CAST VIEW MODEL CLEARED")
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
    }
}