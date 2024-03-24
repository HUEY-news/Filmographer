package com.houston.filmographer.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houston.filmographer.domain.db.HistoryInteractor
import com.houston.filmographer.domain.model.Movie
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractor
): ViewModel() {

    private val stateLiveData = MutableLiveData<HistoryState>()
    fun observeState(): LiveData<HistoryState> = stateLiveData
    private fun renderState(state: HistoryState) { stateLiveData.postValue(state) }

    fun fillData() {
        renderState(HistoryState.Loading)
        viewModelScope.launch {
            interactor
                .historyMovies()
                .collect { movies -> processResult(movies) }
        }
    }

    private fun processResult(movies: List<Movie>) {
        if (movies.isEmpty()) renderState(HistoryState.Empty("Ничего ещё не искали"))
        else renderState(HistoryState.Content(movies))
    }
}