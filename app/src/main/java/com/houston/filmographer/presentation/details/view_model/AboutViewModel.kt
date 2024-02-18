package com.houston.filmographer.presentation.details.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.presentation.details.AboutState

class AboutViewModel(
    private val movieId: String,
    private val interactor: MovieInteractor
): ViewModel() {

    private val key = "k_zcuw1ytf"

    private val stateLivedata = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLivedata

    init {
        interactor.getMovieDetails(key, movieId, object: MovieInteractor.MovieDetailsConsumer {
            override fun consume(data: MovieDetails?, message: String?) {
                if (data != null) stateLivedata.postValue(AboutState.Content(data))
                else stateLivedata.postValue(AboutState.Error(message ?: "Неизвестная ошибка"))
            }
        })
    }
}