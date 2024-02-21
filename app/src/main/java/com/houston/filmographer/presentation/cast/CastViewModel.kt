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
                if (data != null) stateLiveData.postValue(CastState.Content(data))
                else stateLiveData.postValue(CastState.Error(message ?: "Неизвестная ошибка"))
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "CAST VIEW MODEL CLEARED")
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
    }
}