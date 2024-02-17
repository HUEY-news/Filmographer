package com.houston.filmographer.data.impl

import android.content.SharedPreferences

class LocalStorage(private val prefs: SharedPreferences) {
    private companion object {
        const val FAVORITES_KEY = "FAVORITES"
    }

    fun addToFavorites(movieId: String) {
        changeFavorites(movieId = movieId, remove = false)
    }

    fun removeFromFavorites(movieId: String) {
        changeFavorites(movieId = movieId, remove = true)
    }

    fun getSavedFavorites(): Set<String> {
        return prefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    private fun changeFavorites(movieId: String, remove: Boolean) {
        val mutableSet = getSavedFavorites().toMutableSet()
        val modified = if (remove) mutableSet.remove(movieId) else mutableSet.add(movieId)
        if (modified) prefs.edit().putStringSet(FAVORITES_KEY, mutableSet).apply()
    }
}