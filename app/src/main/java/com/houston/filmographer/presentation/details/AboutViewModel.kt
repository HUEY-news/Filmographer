package com.houston.filmographer.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houston.filmographer.domain.MovieInteractor
import kotlinx.coroutines.launch

class AboutViewModel(
    private val movieId: String,
    private val interactor: MovieInteractor
) : ViewModel() {

    private val stateLivedata = MutableLiveData<AboutState>()
    fun observeState(): LiveData<AboutState> = stateLivedata

    init {
        viewModelScope.launch {
            interactor
                .getMovieDetails(TV_API_KEY, movieId)
                .collect { pair ->
                    if (pair.first != null) stateLivedata.postValue(AboutState.Content(pair.first!!))
                    else stateLivedata.postValue(AboutState.Error(pair.second ?: "Неизвестная ошибка"))
                }
        }
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
    }
}