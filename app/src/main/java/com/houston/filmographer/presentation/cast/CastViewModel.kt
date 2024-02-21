package com.houston.filmographer.presentation.cast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.MovieCast

class CastViewModel(
    private val movieId: String,
    private val interactor: Interactor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CastState>()
    fun observeState(): LiveData<CastState> = stateLiveData

    init {
        Log.v("TEST", "CAST VIEW MODEL CREATED")
        stateLiveData.postValue(CastState.Loading)
        interactor.getMovieCast(TV_API_KEY, movieId, object : Interactor.MovieCastConsumer {
            override fun consume(data: MovieCast?, message: String?) {
                if (data != null) stateLiveData.postValue(dataToCastState(data))
                else stateLiveData.postValue(CastState.Error(message ?: "Неизвестная ошибка"))
            }
        })
    }

    private fun dataToCastState(data: MovieCast): CastState {
        val items = buildList<CastItem> {
            if (data.directors.isNotEmpty()) {
                this += CastItem.HeaderItem("Режиссёры")
                this += data.directors.map { person -> CastItem.PersonItem(person) }
            }
            if (data.writers.isNotEmpty()) {
                this += CastItem.HeaderItem("Сценаристы")
                this += data.writers.map { person -> CastItem.PersonItem(person) }
            }
            if (data.actors.isNotEmpty()) {
                this += CastItem.HeaderItem("Актёры")
                this += data.actors.map { person -> CastItem.PersonItem(person) }
            }
            if (data.others.isNotEmpty()) {
                this += CastItem.HeaderItem("Другие")
                this += data.others.map { person -> CastItem.PersonItem(person) }
            }
        }

        return CastState.Content(
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