package com.houston.filmographer.presentation.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.presentation.ToastState
import com.houston.filmographer.util.debounce
import kotlinx.coroutines.launch

class NameViewModel(
    private val interactor: Interactor
) : ViewModel() {

    private var lastQuery: String? = null

    private val stateLiveData = MutableLiveData<NameState>()
    fun observeState(): LiveData<NameState> = stateLiveData

    private val toastLiveData = MutableLiveData<ToastState>(ToastState.None)
    fun observeToast(): LiveData<ToastState> = toastLiveData
    private fun showToast(message: String) {
        toastLiveData.postValue(ToastState.Show(message))
    }

    fun switchToastState() {
        toastLiveData.postValue(ToastState.None)
    }

    val nameSearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true
    ) { text ->
        searchName(TV_API_KEY, text)
    }

    fun searchDebounce(text: String) {
        if (lastQuery != text) {
            lastQuery = text
            val currentQuery = lastQuery ?: ""
            nameSearchDebounce(currentQuery)
        }
    }

    fun sendRequest(text: String) {
        lastQuery = text
        val currentQuery = lastQuery ?: ""
        nameSearchDebounce(currentQuery)
    }

    private fun renderState(state: NameState) {
        stateLiveData.postValue(state)
    }

    private fun searchName(key: String, query: String) {
        if (query.isNotEmpty()) {
            renderState(NameState.Loading)
            viewModelScope.launch {
                interactor
                    .searchName(key, query)
                    .collect { pair ->
                        if (pair.first != null) renderState(NameState.Content(pair.first!!))
                        if (pair.second != null) {
                            renderState(NameState.Error("Ошибка сети"))
                            showToast(pair.second!!)
                        } else if (pair.first?.isEmpty()!!) {
                            renderState(NameState.Empty("Ничего не найдено"))
                        }
                    }
            }
        } else {
            renderState(NameState.Empty("Ничего не найдено"))
            showToast("Поле ввода пустое")
        }
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}