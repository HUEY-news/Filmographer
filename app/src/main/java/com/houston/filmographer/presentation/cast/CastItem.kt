package com.houston.filmographer.presentation.cast

import com.houston.filmographer.domain.model.Person

sealed interface CastItem {
    data class HeaderItem(val header: String): CastItem
    data class PersonItem(val person: Person): CastItem
}