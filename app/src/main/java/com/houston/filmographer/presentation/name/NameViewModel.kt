package com.houston.filmographer.presentation.name

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.model.Person
import com.houston.filmographer.presentation.ToastState

class NameViewModel(
    private val interactor: Interactor
) : ViewModel() {

    init { Log.v("TEST", "NAME VIE MODEL CREATED") }

    private val handler = Handler(Looper.getMainLooper())
    private var lastQuery: String? = null

    private val searchRunnable = Runnable {
        val currentQuery = lastQuery ?: ""
        searchName(TV_API_KEY, currentQuery)
    }

    private val stateLiveData = MutableLiveData<NameState>()
    fun observeState(): LiveData<NameState> = stateLiveData

    private val toastLiveData = MutableLiveData<ToastState>(ToastState.None)
    fun observeToast(): LiveData<ToastState> = toastLiveData
    private fun showToast(message: String) { toastLiveData.postValue(ToastState.Show(message)) }
    fun switchToastState() { toastLiveData.postValue(ToastState.None) }

    fun searchDebounce(text: String) {
        if (lastQuery == text) return
        lastQuery = text
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun sendRequest(text: String) {
        lastQuery = text
        handler.removeCallbacks(searchRunnable)
        handler.post(searchRunnable)
    }

    private fun renderState(state: NameState) { stateLiveData.postValue(state) }

    private fun searchName(key: String, query: String) {
        if (query.isNotEmpty()) {
            renderState(NameState.Loading)
            interactor.searchName(key, query, object : Interactor.NameSearchConsumer {
                override fun consume(data: List<Person>?, message: String?) {
                    if (data != null) renderState(NameState.Content(data))
                    if (message != null) {
                        renderState(NameState.Error("Ошибка сети"))
                        showToast(message)
                    } else if (data?.isEmpty()!!) {
                        renderState(NameState.Empty("Ничего не найдено"))
                    }
                }
            })
        } else {
            renderState(NameState.Empty("Ничего не найдено"))
            showToast("Поле ввода пустое")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.v("TEST", "NAME VIEW MODEL CLEARED")
        handler.removeCallbacksAndMessages(searchRunnable)
    }

    companion object {
        private const val TV_API_KEY = "k_zcuw1ytf"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}