package com.houston.filmographer.presentation.cast

import com.houston.filmographer.domain.model.Person

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - заменить sealed interface CastItem на sealed interface CastItem: ViewItem

sealed interface MovieCastItem: ViewItem {
    data class HeaderItem(val header: String): MovieCastItem
    data class PersonItem(val person: Person): MovieCastItem
}