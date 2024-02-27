package com.houston.filmographer.presentation.name

import com.houston.filmographer.domain.model.Person

sealed interface NameState {
    object Loading : NameState
    data class Content(val data: List<Person>) : NameState
    data class Error(val message: String) : NameState
    data class Empty(val message: String) : NameState

}