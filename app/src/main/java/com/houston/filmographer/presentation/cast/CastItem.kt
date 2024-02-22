package com.houston.filmographer.presentation.cast

import com.houston.filmographer.domain.model.Person

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - заменить sealed interface CastItem на sealed interface CastItem: ViewItem

sealed interface CastItem: ViewItem {
    data class HeaderItem(val header: String): CastItem
    data class PersonItem(val person: Person): CastItem
}