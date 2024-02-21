package com.houston.filmographer.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.MovieDetails

class AboutViewModel(
    private val movieId: String,
    private val interactor: Interactor
) : ViewModel() {

    private val stateLivedata = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLivedata

    init {
        interactor.getMovieDetails(TV_API_KEY, movieId, object : Interactor.MovieDetailsConsumer {
            override fun consume(data: MovieDetails?, message: String?) {
                if (data != null) stateLivedata.postValue(AboutState.Content(data))
                else stateLivedata.postValue(AboutState.Error(message ?: "Неизвестная ошибка"))
            }
        })
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
    }
}